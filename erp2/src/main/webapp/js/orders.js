
//获得订单状态
function getState(value) {
	if(Request['type'] == 1){
		switch(value) {
			case '0' : return '未审核';
			case '1' : return '已审核';
			case '2' : return '已确认';
			case '3' : return '已入库';
			default : return '';
		}
	}
	if(Request['type'] == 2){
		switch(value) {
			case '0' : return '未出库';
			case '1' : return '已出库';
			default : return '';
		}
	}
}
var excel_out;

 // 获取订单明细的状态
function getDetailState(value){
	if(Request['type'] == 1){
		switch(value){
			case '0':return '未入库';
			case '1':return '已入库';
			default: return '';
		}
	}
	if(Request['type'] == 2){
		switch(value){
			case '0':return '未出库';
			case '1':return '已出库';
			default: return '';
		}
	}
}


//根据订单类型，获取不同的列
function getColumns(){
	if(Request['type'] == 1){
		return [[    
				{field:'id',title:'编号',width:100},   
				{field:'createtime',title:'生成日期',width:100,align:'right'},   
				{field:'checktime',title:'审核日期',width:100,align:'right'},   
				{field:'starttime',title:'确认日期',width:100,align:'right'},   
				{field:'endtime',title:'入库或出库日期',width:100,align:'right'},   
				{field:'createrName',title:'下单员',width:100,align:'right'},   
				{field:'checkerName',title:'审核员',width:100,align:'right'},   
				{field:'starterName',title:'采购员',width:100,align:'right'},   
				{field:'enderName',title:'库管员',width:100,align:'right'},   
				{field:'supplierName',title:'供应商',width:100,align:'right'},   
				{field:'totalmoney',title:'合计金额',width:100,align:'right'},   
				{field:'state',title:'状态',width:100,align:'right',formatter:function(value){
					return getState(value);
				}},   
 	        	{field:'waybillsn',title:'运单号',width:100,align:'right'}
 	    ]];
	}
	if(Request['type'] == 2){
		return [[
		            {field:'id',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100},
		  		    {field:'endtime',title:'出库日期',width:100},
		  		    {field:'createrName',title:'下单员',width:100},
		  		    {field:'enderName',title:'库管员',width:100},
		  		    {field:'supplierName',title:'客户',width:100},
		  		    {field:'totalmoney',title:'合计金额',width:100},
		  		    {field:'state',title:'状态',width:100,formatter:getState},
		  		    {field:'waybillsn',title:'运单号',width:100}
				]];
	}
}

$(function(){

	var url = 'orders_page';
	var btnText = "";
	var inoutTitle = "";

	//我的订单
	if(Request['oper'] == 'myOrders'){
		if(Request['type'] == 1) {
			url = 'orders_myPage?type=1';
			document.title = '我的采购订单';
			btnText = '采购订单';
			//显示供应商
			$('#addOrdersSupplier').html('供应商');
		}
		if(Request['type'] == 2) {
			url = 'orders_myPage?type=2';
			document.title = '我的销售订单';
			btnText = '销售订单';
			//显示供应商
			$('#addOrdersSupplier').html('客户');
		}
	}

	//采购订单查询
	if(Request['oper'] == 'orders' && Request['type'] == 1){
		url += '?type=1';
		document.title = '采购订单查询';
	}
	//采购订单查询
	if(Request['oper'] == 'orders' && Request['type'] == 2){
		url += '?type=2';
		document.title = '销售订单查询';
	}

	//如果审核业务，加上state=0，只查询出未审核的订单
	if(Request['oper'] == 'doCheck'){
		url += "?type=1&state=0";
		document.title="采购订单审核";
	}
	//如果确认业务，加上state=1，只查询出已审核过的订单
	if(Request['oper'] == 'doStart'){
		url += "?type=1&state=1";
		document.title="采购订单确认";
	}
	//如果入库业务，加上state=2，只查询出已确认过的订单
	if(Request['oper'] == 'doInStore'){
		url += "?type=1&state=2";
		document.title="采购订单入库";
		inoutTitle = '入库';
	}
	if(Request['oper'] == 'doOutStore'){
		url += "?type=2&state=0";
		document.title="销售订单入库";
		inoutTitle = '出库';
	}

	$('#dg').datagrid({
		url:url,    
 	    columns:getColumns(),
 	    pagination:true,
 	    singleSelect:true,
		rownumbers:true,
		fitColumns:true,
		onDblClickRow:function(rowIndex, rowData){
			//显示订单详情内容
			$('#id').html(rowData.id);
			$('#suppliername').html(rowData.supplierName);
			$('#creater').html(rowData.createrName);
			$('#checker').html(rowData.checkerName);
			$('#starter').html(rowData.starterName);
			$('#ender').html(rowData.enderName);
			$('#createtime').html(rowData.createtime);
			$('#checktime').html(rowData.checktime);
			$('#starttime').html(rowData.starttime);
			$('#endtime').html(rowData.endtime);
			/*var temp=JSON.stringify(rowData);
			alert(temp);*/
			excel_out=rowData;
			//打开窗口
			$('#ordersDlg').dialog('open');
			//记载明细数据
			$('#itemgrid').datagrid('loadData',rowData.orderdetails);
			
		}
	})
	//明细表格
	$('#itemgrid').datagrid({    
 	    columns:[[    
 	        {field:'id',title:'编号',width:100},   
 	        {field:'goodsid',title:'商品编号',width:100},   
 	        {field:'goodsname',title:'商品名称',width:100},   
 	        {field:'price',title:'价格',width:100},   
 	        {field:'num',title:'数量',width:100},   
 	        {field:'money',title:'金额',width:100},   
 	        {field:'state',title:'状态',width:100,formatter:getDetailState}
 	    ]],
 	    singleSelect:true,
		fitColumns:true
	})

	//添加审核按钮
	if(Request['oper'] == 'doCheck'){
		$('#ordersDlg').dialog({
			toolbar:[{
				text:'审核订单',
				iconCls:'icon-search',
				handler:doCheck
			}]
		});
	}
	//添加导出按钮
	$('#ordersDlg').dialog({
		toolbar:[{
			text:'导出',
			iconCls:'icon-excel',
			handler:doExport
	}]
	});
		/*{
		text:'导出',
		iconCls:'icon-excel',
		handler:doExport
		}*/
	//添加确认按钮
	if(Request['oper'] == 'doStart'){
		$('#ordersDlg').dialog({
			toolbar:[{
				text:'确认订单',
				iconCls:'icon-search',
				handler:doStart
			}]
		});
	}
	
	//添加出入库双击事件
	if(Request['oper'] == 'doInStore' ||  Request['oper'] == 'doOutStore'){
		$('#itemgrid').datagrid({
			onDblClickRow:function(rowIndex, rowData){

				//如果已入库则提示
				if($('#itemgrid').datagrid('getSelected').state == 1) {
					$.messager.alert('警告','已经入库！');
					return;
				}

				//显示数据
				$('#itemid').val(rowData.id);
				$('#goodsid').html(rowData.goodsid);
				$('#goodsname').html(rowData.goodsname);
				$('#goodsnum').html(rowData.num);
				//打开入库窗口
				$('#itemDlg').dialog('open');
			}
		});
	}

	//添加采购申请按钮
	if(Request['oper'] == 'myOrders'){
		$('#dg').datagrid({
			toolbar:[
			   {
				   text:btnText,
				   iconCls:'icon-add',
				   handler:function(){
					   $('#addOrdersDlg').dialog('open');
				   }
			   }   
			]
		});
	}
	
	//出入库窗口
	$('#itemDlg').dialog({
		width:300,
		height:200,
		title:inoutTitle,
		modal:true,
		closed:true,
		buttons:[
		   {
			   text:inoutTitle,
			   iconCls:'icon-save',
			   handler:doInOutStore
		   }
		]
	});

	//增加订单的窗口
	$('#addOrdersDlg').dialog({
		title:'增加订单',
		width:800,
		height:400,
		modal:true,
		closed:true
	});

})

//点击审核订单
function doCheck() {
	$.messager.confirm('确认','您想要审核订单吗？',function(r){    
			if (r){    
				
				$.ajax({
					type:'POST',
					url:'orders_doCheck?id='+$('#id').html(),
					success:function(data){
						$.messager.alert('我的消息',data.msg,'info',function(){
							if(data.success){
								$('#ordersDlg').dialog('close');
								$('#dg').datagrid('reload');
							}
						});
					}
				})

			}    
		});
}

//点击确认订单
function doStart() {
	$.messager.confirm('确认','您想要确认订单吗？',function(r){    
			if (r){    
				  
				$.ajax({
					type:'POST',
					url:'orders_doStart?id='+$('#id').html(),
					success:function(data){
						$.messager.alert('我的消息',data.msg,'info',function(){
							if(data.success){
								$('#ordersDlg').dialog('close');
								$('#dg').datagrid('reload');
							}
						});
					}
				})

			}    
		});
}
function doExport(){
	$.ajax({
		url: 'exceldata_out',
		data: excel_out,
		type: 'post',
		success:function(data){
			$.messager.alert('提示',data.msg,'info',function(){
				if(data.success){
					 	alert('excel转换成功');
					}else{
						alert('excel转换失败');
					}
				});
			}
	});
	
}
//点击出入库
function doInOutStore() {

	var message = "";
	var url = "";
	if(Request['type'] == 1){
		message = "确认要入库吗？";
		url = "orderdetail_doInStore";
	}
	if(Request['type'] == 2){
		message = "确认要出库吗？";
		url = "orderdetail_doOutStore";
	}

	var formdata = $('#itemForm').serializeJSON();
	if(formdata.storeid == ''){
		$.messager.alert('提示','请选择仓库!','info');
		return;
	}

	$.messager.confirm("确认",message,function(r){
		if(r){
			$.ajax({
				url: url,
				data: formdata,
				type: 'post',
				success:function(data){
					$.messager.alert('提示',data.msg,'info',function(){
						if(data.success){
							//关闭入库窗口
							$('#itemDlg').dialog('close');
							//设置明细的状态
							$('#itemgrid').datagrid('getSelected').state = "1";
							//刷新明细列
							var itemgridData = $('#itemgrid').datagrid('getData');
							$('#itemgrid').datagrid('loadData',itemgridData);
							//如果所有明细都 入库了，应该关闭订单详情，并且刷新订单列表
							var allIn = true;
							$.each(itemgridData.rows,function(i,row){
								if(row.state * 1 == 0){
									allIn = false;
									//跳出循环
									return false;
								}
							});
							if(allIn == true){
								//关闭详情窗口
								$('#ordersDlg').dialog('close');
								//刷新订单列表
								$('#dg').datagrid('reload');
							}
						}
					});
				}
			});
		}
	});
}