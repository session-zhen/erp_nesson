
//页面加载完毕之后
$(function(){
	
	//加载数据表格
	$('#dg').datagrid({    
 	   url:'report_all',    
 	   columns:[[    
		 {field:'name',title:'商品类型',width:100,align:'right'},
		 {field:'total',title:'销售额',width:100,align:'right'}
	 	]],
	   singleSelect:true,
	   onLoadSuccess:function(data){
			//alert(JSON.stringify(data.rows));
			//显示图
			showChart(data.rows);
	   }
 	});
	
	//点击查询
	$('#submitSearch').click(function(){
		//查询条件表单序列化
		var formData = $('#searchForm').serializeJSON();
		
		//alert(JSON.stringify(formData));
		//条件查询
		$('#dg').datagrid('load',formData);
	})
})


function showChart(data){
	var dom = document.getElementById("pieChart");
	var myChart = echarts.init(dom);
	var app = {};

	for(var i = 0;i<data.length;i++) {
		data[i].value = data[i].total;
	}

	option = null;
	option = {
		title : {
			text: '统计销售',
			x:'center'
		},
		tooltip : {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: data.name
		},
		series : [
			{
				name: '访问来源',
				type: 'pie',
				radius : '55%',
				center: ['50%', '60%'],
				data:data,
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				}
			}
		]
	};
;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
}