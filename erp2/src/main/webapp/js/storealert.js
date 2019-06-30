$(function(){
	$('#grid').datagrid({
		url: 'getStoreAlert_all',
		columns:[[
			{field:'id',title:'商品编号',width:100},
			{field:'name',title:'商品名称',width:100},
			{field:'stornum',title:'库存数量',width:100},
			{field:'outnum',title:'待发货数量',width:100}
		]],
		singleSelect:true
	});
});