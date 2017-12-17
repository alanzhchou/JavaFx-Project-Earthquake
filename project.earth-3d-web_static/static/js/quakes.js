var locations = {
	obj1: {
		alpha: Math.PI / 2,//与地心连线 和 地轴的角度-----相当于纬度，0度在北极
		delta: 0,//地球表面该点与地心连线和赤道上某一固定点的角度------相当于经度,0度在子午线
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
			defaultSpeed: 10,
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
	$('#dateFrom').datetimepicker({startDate:	'2016/04/15 05:03', value:'2016/04/15 05:03'});
    $('#dateTo').datetimepicker({startDate:	'2017/12/15 05:03', value:'2017/12/15 05:03'});

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
		var dataObj = {"dataFrom": _dateFrom.val(),"dateTo": _dateTo.val(),
						"lon": _lon.val(), "lat": _lat.val(),
						"depth": _depth.val(), "mag": _mag.val()};		
		var params = JSON.stringify(dataObj);

		$.get("http://httpbin.org/get",params,function(data,status){
			// console.log(data.args);

			//让后台直接返回JSON化的对象，这里直接接受参数调用渲染函数
			// locations = data;
			// selectExample('locations');

			//这里用作测试生成若干个随机对象数据，来调用渲染
			var rand = 4; 
			locations = {};
			for(let i=0;i<50;i++){
				locations['obj' + rand] = {
					alpha: parseInt(Math.random()*8) * Math.PI / 8,
					delta: parseInt(Math.random()*100) * Math.PI / 4,
					Region: 'China-3',
					date: '2017.10.05',
					depth: 65,
					mag: parseInt(Math.random()*25)
				};
				rand++;
			}
			selectExample('locations');
		})	
		return false;
	});
});