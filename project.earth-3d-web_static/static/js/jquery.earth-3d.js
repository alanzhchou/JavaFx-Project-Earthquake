var earth3d;
(function($) {
	$.widget('ui.earth3d', 
		{
			options: {
				texture: 'static/images/earth1024x1024.jpg',
				sphere: {
					tilt: 0,
					turn: 0,
					r: 10
				},

				defaultSpeed: 20,
				backToDefaultTime: 4000,
				locations: {},
				
				dragElement: null,
				locationsElement: null,

				tiling: {horizontal: 1, vertical: 1},

				onInitLocation: function(location, widget) {
				var fix = ['<p style="color:yellow; font-size:10px; display:none;">','</p>'];
				var centreInfo = fix[0] + 'Region: ' + location.Region + fix[1] + 
								 fix[0] + 'date: ' + location.date + fix[1] + 
								 fix[0] + 'depth: ' + location.depth + fix[1] +
								 fix[0] + 'Mag: ' + location.mag + fix[1];
				var $elem = $('<div class="location">' + centreInfo + '</div>');
				$elem.css({width:(location.mag*2+5)+'px',height:(location.mag+5)+'px','margin-top': -(location.mag+3)+'px','margin-left': -(location.mag+3)+'px'});
				$elem.appendTo(widget.options.locationsElement);
				$elem.mouseenter(function() {
					$(this).children().css('display','block');
				});
				$elem.mouseleave(function() {
					$(this).children().css('display','none');
				});
				location.$element = $elem;
				},
				onShowLocation: function(location, x, y) {
				location.$element.show();
				},
				onRefreshLocation: function(location, x, y) {
				//console.log(x, y);
				location.$element.css({left: x,top: y
				});
				},
				onHideLocation: function(location, x, y) {
				location.$element.hide();
				},
				onDeleteLocation: function(location) {
				location.$element.remove();
				},
			},

			earth: null,
			posVar: 24 * 3600 * 1000,
			lastMousePos: null,
			lastSpeed: null,
			lastTime: null,
			lastTurnByTime: 20000,
			textureWidth: null,
			textureHeight: null,

			renderAnimationFrameId: null,
			mousePressed: null,

			_create: function() {
				earth3d = this;
				var self = this;
				createSphere(this.element[0], this.options.texture, function(earth, textureWidth, textureHeight) { self._onSphereCreated(earth, textureWidth, textureHeight); }, this.options.tiling);
				if (this.options.dragElement !== null) {
				this.options.dragElement
				.bind('mousedown', function(e) {
					self._mouseDragStart(e);
					self.mousePressed = true;
				})
				.bind('mouseup', function(e) {
					self._mouseDragStop(e);
					self.mousePressed = false;
				})
				.bind('mousemove', function(e){
					if (self.mousePressed) {
						self._mouseDrag(e);
					}
				});
				}
				this._initLocations();
			},

			_initLocations: function() {
				for (var key in this.options.locations) {
					var location = this.options.locations[key];
					location.visible = true;
					this.options.onInitLocation(location, this);
				}
			},

			getSphereRadiusInPixel: function() {
				return this.earth.getRadius() / 2;
			},

			_onSphereCreated: function(earth, textureWidth, textureHeight) {
				var self = this;
				this.textureWidth = textureWidth;
				this.textureHeight = textureHeight;
				this.earth = earth;
				this.earth.init(this.options.sphere);
				this.earth.turnBy = function(time) {
					return self._turnBy(time); 
				};
				var renderAnimationFrame = function(/* time */ time) {
					/* time ~= +new Date // the unix time */
					earth.renderFrame(time);
					self._renderAnimationFrame(time);
					self.renderAnimationFrameId = window.requestAnimationFrame(renderAnimationFrame);
				};
				this.renderAnimationFrameId = window.requestAnimationFrame(renderAnimationFrame);
			},

			destroy: function() {
				window.cancelAnimationFrame(this.renderAnimationFrameId);
			},

			_renderAnimationFrame: function(time) {
				var ry=90+this.options.sphere.tilt;
				var rz=180+this.options.sphere.turn;

				var RY = (90-ry);
				var RZ = (180-rz);
				var RX = 0,RY,RZ;

				var rx=RX*Math.PI/180;
				var ry=RY*Math.PI/180;
				var rz=RZ*Math.PI/180;
				//console.log(rx, ry, rz);
				var r = this.getSphereRadiusInPixel();

				var center = {
				x: this.element.width() / 2,
				y: this.element.height() / 2
				}

				for (var key in this.options.locations) {
				var location = this.options.locations[key];

				if (typeof location.delta === 'undefined') {
					location.flatPosition = {x: location.x, y: location.y};
					this.options.onRefreshLocation(location, location.x, location.y, this);
					continue;
				}

				/*
					WARNING: calculation of alphaAngle and deltaAngle is not exact
					I had to create the _calibrated functions to modify the deltaAngle to make the result look good on
					a spinning planet without rotation. It will totally bug with rotation!
				* */
				var progression = (((this.posVar + this.textureWidth * location.delta / (2 * Math.PI)) % this.textureWidth) / this.textureWidth);
				var alphaAngle = progression * 2 * Math.PI;
				var deltaAngle = this._calibrated(progression, location.alpha) * 2 * Math.PI;


				var objAlpha = ry + location.alpha - Math.sin(alphaAngle / 2) * 0.15 * (location.alpha - Math.PI / 2) / (Math.PI / 4);
				var objDelta = rz + deltaAngle;

				var a = this._orbitalTo3d(objAlpha, objDelta, r);

				var flatPosition = this._orthographicProjection(a);

				if (a.x < 0 && !location.visible) {
					this.options.onShowLocation(location, flatPosition.x, flatPosition.y, this);
				}
				if (a.x > 0 && location.visible) {
					this.options.onHideLocation(location, flatPosition.x, flatPosition.y, this);
				}
				this.options.onRefreshLocation(location, flatPosition.x, flatPosition.y, this);

				location.visible = a.x < 0;
				location.position = a;
				location.flatPosition = flatPosition;
				location.rAlpha = objAlpha;
				location.rDelta = objDelta;
				}
			},

			// WARNING: temporary function to make the locations look good on a spinning planet without rotation
			_calibrated: function(x, alpha) {
				var calib = 0.3 + 0.15 * Math.abs(alpha - Math.PI / 2) / (Math.PI / 4);
				var y = calib * (4 * (x - 0.5) * (x - 0.5) * (x - 0.5) + 0.5) + (1 - calib) * x;
				return y;
			},

			_orbitalTo3d: function(alpha, delta, r) {
				return {
				x: -r * Math.sin(alpha) * Math.cos(delta),
				y: -r * Math.sin(alpha) * Math.sin(delta),
				z: -r * Math.cos(alpha)
				};
			},

			_orthographicProjection: function (position) {
				return { x: position.y + this.element.width() / 2, y: position.z + this.element.height() / 2 };
			},

			_mouseDragStart: function (e) {
				this.lastMousePos = e.clientX;
				this.lastSpeed = null;
			},

			_mouseDrag: function (e) {
				this.lastSpeed = (e.clientX - this.lastMousePos);
				this.posVar = this.posVar - this.lastSpeed;
				this.lastMousePos = e.clientX;
			},

			_mouseDragStop: function (e) {
				this.lastMousePos = null;
				this.lastTime = null;
			},

			_turnBy: function (time) {
				if (this.lastTurnByTime === null) {
					this.lastTurnByTime = time;
				}
				var timeDiff = (time - this.lastTurnByTime) / 1000;
				if (this.lastMousePos === null) {
					if (this.lastSpeed !== null) {
						if (this.lastTime === null) {
							this.lastTime = time;
						}
						if (this.options.backToDefaultTime + this.lastTime - time < 0) {
							this.lastSpeed = null;
						} else {
							var backToDef = (this.options.backToDefaultTime + this.lastTime - time) / this.options.backToDefaultTime;
							this.posVar -= this.lastSpeed * backToDef + (this.options.defaultSpeed * timeDiff) * (1 - backToDef);
						}
					} else {
						this.posVar -= this.options.defaultSpeed * timeDiff;
					}
				}
				this.lastTurnByTime = time;
				return this.posVar;
			},

			changeLocations: function (locations) {
				for (var key in this.options.locations) {
					this.options.onDeleteLocation(this.options.locations[key], this);
				}
				this.options.locations = locations;
				this._initLocations();
			},
		}
	);
})($);