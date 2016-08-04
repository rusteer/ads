<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i> 综合统计<span class="divider"> <i class="icon-angle-right arrow-icon"></i> </span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<div class="btn-group">
					<input type="text" class="input-small" id="fromDate" value="${fromDate}" placeholder="起始日期"> 
					<input type="text" class="input-small" id="toDate" value="${toDate}" placeholder="结束日期"> 
					<select class="input-large" id="systemApp">
						<option value="-1">全部设备</option>
						<option value="0" ${0==param.systemApp?"selected":"" }>无System权限</option>
						<option value="1" ${1==param.systemApp?"selected":"" }>有System权限</option>
					</select>						
				</div>
			</div>
			<div class="hr"></div>	 		
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2" >
			    <thead>
			        <tr>
			        	<th>日期 </th>
			            <th>激活设备数</th>
			        </tr>
			    </thead>
			    <tbody>
			    <c:set var="activateCount" value="0"/>
			    <c:forEach var="entity" items="${list}">
			    	 <c:set var="activateCount" value="${activateCount+entity.activateCount}"/>
			    	 <tr class="odd gradeX">
			    	 	<td> ${entity.statDate}</td>
			    	 	<td>${entity.activateCount}</td>
			    	 </tr>
			    </c:forEach>
 					<tr class="odd gradeX">
			    	 	<th colspan="1">总计</th>
			    	 	<th>${activateCount}</th>
			    	 </tr>			    
			    </tbody>
			</table>
		</div>
	</div>
</div>
<script>
$(function() {
	 var idList=[ "fromDate","toDate","systemApp"];
	 var refreshList=function(){
        var url="?";
        _(idList).each(function(id){
        	var value=$("#"+id).val();
        	if(value) url+="&"+id+"="+value;
        });
        window.location.href=url;
    };  
   _(idList).each(function(id){
	   if(id.indexOf("Date")>0){
		$("#"+id).datepicker({format:"yyyy-mm-dd"}).on('changeDate', function(ev){refreshList();});
	   }else{
	    $("#"+id).change(function(){ refreshList(); });
	   }
   }); 
});
</script>
	