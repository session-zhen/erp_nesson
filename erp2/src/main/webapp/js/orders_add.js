
var existEditIndex = -1;

$(function(){

	$('#grid').datagrid({   
	 	   columns:[[    
		    	{field:'goodsid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
		    		disabled:true
		    	}}},    
	 	        {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
	 	        	valueField: 'name',    
	 	        	textField: 'name',    
	 	        	url: 'goods_all',    
	 	        	onSelect: function(goods){    
	 	        		var goodsidEditor = getEditor('goodsid');
	 	        		var priceEditor = getEditor('price');
	 	        		var numEditor = getEditor('num');
	 	        		//设置id
	 	        		goodsidEditor.target.val(goods.id);
						//设置价格
						if(Request['type'] == 1) {
							priceEditor.target.val(goods.inprice);
						}
						if(Request['type'] == 2) {
							priceEditor.target.val(goods.outprice);
						}

	 	        		numEditor.target.select();
	 	        		//键盘事件
	 	        		numKeyAction();
	 	        		//计算总额
		 	       		cal();
		 	       		//计算总计
		 	       		sum();
	 	        		
	 	           }
		    	}}},    
	 	        {field:'price',title:'价格',width:100,align:'right',editor:{type:'numberbox',options:{
	 	        	precision:2,
		    		disabled:true
		    	}}},   
	 	        {field:'num',title:'数量',width:100,align:'right',editor:'numberbox'},
	 	        {field:'money',title:'总额',width:100,align:'right',editor:{type:'numberbox',options:{
	 	        	precision:2,
		    		disabled:true
		    	}}},   
	 	        {field:'-',title:'操作',width:150,align:'right',formatter: function(value,row,index){
					
	 	        	if(row.num == '总计') {
	 	        		return;
	 	        	}
	 	        	return '<a href="javascript:void(0)" onclick="deleteRow('+ index +')">删除</a>'
	 	        	
				}}    
		   ]],
	 	   toolbar: [{
	 			text:'增加',
	 			iconCls: 'icon-add',
	 			handler: function(){
	 				//如果在编辑，就结束编辑
	 				if(existEditIndex>-1) {
	 					$('#grid').datagrid('endEdit',existEditIndex);
	 				}
	 				//添加一行
	 				$('#grid').datagrid('appendRow',{num:0,money:0});
	 				//指向新添的一行
	 				existEditIndex = $('#grid').datagrid('getRows').length - 1;
	 				//启动编辑
	 				$('#grid').datagrid('beginEdit',existEditIndex);
	 			}
	 		},'-',{
	 			text:'提交',
	 			iconCls: 'icon-save',
	 			handler: function(){
	 				//供应商要选择
	 				if($('#supplier').combogrid('getValue') == '') {
	 					$.messager.alert('请选择供应商','请选择供应商！','info');
	 					return;
	 				}
	 				
	 				//结束编辑
	 				$('#grid').datagrid('endEdit',existEditIndex);
	 				
	 				//选择商品
	 				var rows = $('#grid').datagrid('getRows');
	 				if(rows.length == 0) {
	 					$.messager.alert('请选择商品','请选择商品！','info');
	 					return;
	 				}
	 				
	 				for(var i = 0;i<rows.length;i++) {
	 					if(rows[i].money == 0) {
	 						$.messager.alert('商品的信息不完全','商品的信息不完全！','info');
		 					return;
	 					}
	 				}
	 				
	 				//供应商
	 				var formData = $('#addForm').serializeJSON();
	 				//添加商品数据
	 				formData.json = JSON.stringify(rows);
	 				
	 				//ajax提交后台
	 				$.ajax({
	 					type:'POST',
	 					url:'orders_save?type=' + Request['type'],
	 					data:formData,
	 					success:function(data){
	 						//插入成功
	 						if(data.success) {
	 							//清除供应商
	 							$('#supplier').combogrid('clear');
	 							//清除商品信息
								$('#grid').datagrid('loadData',{total:0,rows:[],footer:[{num:'总计',money:0}]});
								//关闭增加订单的窗口
								$('#addOrdersDlg').dialog('close');
								//刷新订单列表
								$('#dg').datagrid('reload');
	 						}
	 					}
	 					
	 				})
	 			}
	 		}],
	 		onClickRow:function(rowIndex, rowData) {
	 			
	 			//结束编辑
	 			$('#grid').datagrid('endEdit',existEditIndex);
	 			existEditIndex = rowIndex;
	 			//开启编辑
	 			$('#grid').datagrid('beginEdit',existEditIndex);
	 			//键盘事件
	 			numKeyAction();
	 			
	 		},
	 		singleSelect:true,
	 		showFooter:true,
	 		rownumbers:true
	});
	
	
	//加载页脚
	$('#grid').datagrid('reloadFooter',[{num:'总计',money:0}]);
	
	$('#supplier').combogrid({    
	    panelWidth:450,
	    idField:'id',    
	    textField:'name',    
	    url:'supplier_all?type=' + Request['type'],    
	    columns:[[    
	    	{field:'id',title:'id',width:100},    
 	        {field:'name',title:'厂商',width:100},    
 	        {field:'address',title:'地址',width:100,align:'right'},   
 	        {field:'contact',title:'联系人',width:100,align:'right'},
 	        {field:'tele',title:'电话',width:100,align:'right'},   
 	        {field:'email',title:'邮箱',width:150,align:'right'}    
	    ]]    
	}); 
	
	//测试点击
	$('#btn').click(function(){
		var data = $('#grid').datagrid('getData');
		alert(JSON.stringify(data));
	})
	
})

//获得编辑器的方法
function getEditor(field) {
	return $('#grid').datagrid('getEditor', {index:existEditIndex,field:field});
}

//键盘事件
function numKeyAction() {
	var numEditor = getEditor('num');
	
	numEditor.target.bind('keyup',function(){
		//计算总额
		cal();
		//计算总计
		sum();
	})
}

//计算总额
function cal(){
	var priceEditor = getEditor('price');
	var numEditor = getEditor('num');
	var moneyEditor = getEditor('money');
	
	var price = priceEditor.target.val();
	var num = numEditor.target.val();
	
	var money = (price * num).toFixed(2);
	//设置总额
	moneyEditor.target.val(money);
	//更新表格中的数据,设置row json对象里的key对应的值
	$('#grid').datagrid('getRows')[existEditIndex].money = money;
}

//计算总计
function sum(){
	var rows = $('#grid').datagrid('getRows');
	var total = 0;
	
	//遍历
	$.each(rows,function(i,row){
		total += parseFloat(row.money);
	})
	//计算总额
	total = total.toFixed(2);
	//重新设置
	$('#grid').datagrid('reloadFooter',[{num:'总计',money:total}]);
}

//删除列
function deleteRow(index) {
	
	//结束编辑
	$('#grid').datagrid('endEdit',existEditIndex);
	
	//删除
	$('#grid').datagrid('deleteRow',index);
	
	//计算总计
	sum();
	
}
