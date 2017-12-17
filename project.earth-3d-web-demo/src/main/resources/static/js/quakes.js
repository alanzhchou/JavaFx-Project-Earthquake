var locations = {
	obj1: {
		alpha: Math.PI/2,//与地心连线 和 地轴的角度-----相当于纬度，0度在北极
		delta: -3*Math.PI/2,//地球表面该点与地心连线和赤道上某一固定点的角度------相当于经度,0度在子午线
		Region: 'China-1',
		date: '2017.10.05',
		depth: 50,
		mag: 1
	},
};

var examples = {
	'locations':function(locations) {
		$('#sphere').earth3d({
			locationsElement: $('#locations'), //放置多个location的元素
			sphere: {tilt: 0, turn: 0, r: 10},
			dragElement: $('#locations'), // 鼠标拖动感知
			locations: locations,
			defaultSpeed: 0,
			backToDefaultTime: 1000,
		});
	},
};

function selectExample(example) {
	$('#sphere').earth3d('destroy');
	$('#sphere').replaceWith($('<canvas id="sphere" width="600" height="600"></canvas>'));
	$('.location').remove();
	examples[example](locations);
}

function init_my_pickers(){
	$('#dateFrom').datetimepicker({startDate:	'2017/11/26 00:03', value:'2017/11/26 00:03'});
    $('#dateTo').datetimepicker({startDate:	'2017/11/26 05:03', value:'2017/11/26 05:03'});

	$('.my_lonPicker').jRange({
		from: -180,
		to: 180,
		step: 3,
		scale: [-180,-135,-90,-45,0,45,90,135,180],
		format: '%s',
		width: 200,
		showLabels: true,
		isRange: true
	});

	$('.my_latPicker').jRange({
		from: -90,
		to: 90,
		step: 1,
		scale: [-90,-60,-30,0,30,60,90],
		format: '%s',
		width: 200,
		showLabels: true,
		isRange: true
	});

	$('.my_depthPicker').jRange({
		from: 0,
		to: 1000,
		step: 10,
		scale: [0,200,400,600,800,1000],
		format: '%s',
		width: 200,
		showLabels: true,
		isRange: true
	});

	$('.my_magPicker').jRange({
		from: 0,
		to: 10,
		step: 1,
		scale: [0,2,4,6,8,10],
		format: '%s',
		width: 200,
		showLabels: true,
		isRange: true
	});
}

/* window onload function */
$(function () {
	init_my_pickers();

	selectExample('locations');

	var _dateFrom = $('#dateFrom'),
		_dateTo = $('#dateTo'),
	    _lon = $('#lon'),
		_lat = $('#lat'),
		_depth = $('#depth'),
		_mag = $('#mag');

	$('#form_tog>button:first').click(function(){
		$(this).text()==='显示参数'?$(this).text('隐藏参数'):$(this).text('显示参数');
		$('.condition_choose:first').fadeToggle(300);
	});

	$('.searchForm:first').submit(function(){
		var dataObj = {"dateFrom": _dateFrom.val(),"dateTo": _dateTo.val(),
						"lon": _lon.val(), "lat": _lat.val(),
						"depth": _depth.val(), "mag": _mag.val()};		
		var params = JSON.stringify(dataObj);

		$.post("http://127.0.0.1:8080/getLocations",dataObj,function(data,status){
			data = JSON.parse(data);
			locations = data;

			var length = 0;
			for(var key in locations){
				length++;
			}
			if (length<=500){
                selectExample('locations');
			}else {
				alert("too much( " + length + " locations), look up to your Web brower!");
			}
		})	
		return false;
	});
});