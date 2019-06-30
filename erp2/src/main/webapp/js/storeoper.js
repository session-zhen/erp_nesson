//数据表格的数据
var columns;
//名字
var methodName = "";

//页面加载完毕之后
$(function(){
	
	//加载数据表格
	$('#dg').datagrid({    
 	   url:methodName + '_page',    
 	   columns:columns,
	   pagination:true,
	   singleSelect:true
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
	
	