//数据表格的数据
var columns;
////名字
var methodName = "";
////长宽
var width;
var height;

//参数
var listParam = "";

//页面加载完毕之后
$(function(){
	
	//加载数据表格
	$('#dg').datagrid({    
 	   url:methodName + '_page' + listParam,    
 	   columns:columns,
 	   pagination:true,
 	   toolbar: [{
 		    text:'编辑数据',
 			iconCls: 'icon-edit',
 			handler: edit
 		},'-',{
 			text:'添加数据',
 			iconCls: 'icon-add',
 			handler: add
 		},'-',{
 			text:'删除数据',
 			iconCls: 'icon-remove',
 			handler: deletes
 		}]
 	});
	
	
	$('#dd').dialog({    
	    title: '添加编辑'+methodName+'窗口',    
	    width: width,
	    height: height,
	    closed: true,  
	    modal: true
	});
	
	//点击按钮提交表单
	$('#submitForm').click(function(){
		//表单校验
		var valid = $('#myForm').form('validate');
		//校验没有通过，直接返回
		if(!valid) {
			return;
		} else {
			//表单数据
			var formData = $('#myForm').serializeJSON();
			//ajax提交表单
			$.ajax({
				type:'POST',
				url:methodName+'_addOrEdit' + listParam,
				data:formData,
				success:function(data){
					if(data.success) {
						//关闭添加窗口
						$('#dd').dialog('close');
						//清除数据
						$('#myForm').form('clear');
						
						$.messager.alert('添加编辑','添加编辑成功！','info',function(){
						//重新加载数据表格
							$('#dg').datagrid("reload");	
						});
					} else {
						$.messager.alert('添加编辑','添加编辑失败！','info');
					}
				}
			});
		}
	})
	
	//点击查询
	$('#submitSearch').click(function(){
		//查询条件表单序列化
		var formData = $('#searchForm').serializeJSON();
		
		//alert(JSON.stringify(formData));
		//条件查询
		$('#dg').datagrid('load',formData);
	})
})

//添加员工
function add() {
	
	//清除数据
	$('#myForm').form('clear');
	
	$('#dd').dialog('open'); 
}

//编辑员工
function edit() {	
	var selected = $('#dg').datagrid('getSelected');
	if(selected == null) {
		$.messager.alert('请先选择数据','请先选择数据！','info');
		return;
	}
	$('#myForm').form('load',selected);
	$('#dd').dialog('open');
}

//删除员工
function deletes(){
	var selections = $('#dg').datagrid('getSelections');
	if(selections.length==0) {
		$.messager.alert('请先选择删除数据','请先选择删除数据！','info');
		return;
	}
	
	//确认删除
	$.messager.confirm('确认对话框', '您确认要删除吗？', function(r){
		if (r){
			//把所有选择的数据的id装到一个数组中
			var ids = new Array(selections.length);
			for(var i = 0;i<selections.length;i++) {
				ids[i] = selections[i].id;
			}
			
			ids = JSON.stringify(ids);
			
			//alert(ids);
			
			//触发ajax事件，删除表格
			$.ajax({
				type:'POST',
				url:methodName+'_delete',
				data:{
					ids:ids
				},
				success:function(data) {
					if(data.success) {						
						$.messager.alert('删除数据','删除数据成功！','info',function(){
						//重新加载数据表格
							$('#dg').datagrid("reload");	
						});
					} else {
						$.messager.alert('删除数据','删除数据失败！','info');
					}
				}
			})
			
		}
	});


	
	
}
	
	