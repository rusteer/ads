<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i><a href="/${cmpName}/?fromDate=${param.fromDate}&toDate=${param.toDate}&adsId=${param.adsId}">留存统计</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i> </span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<div class="btn-group">
					<input type="text" class="input-small" id="fromDate" value="${fromDate}" placeholder="起始日期"> 
					<input type="text" class="input-small" id="toDate" value="${toDate}" placeholder="结束日期"> 
					<select class="input-large" id="adsId">
						<option value=""> </option>
						<c:forEach var="entity" items="${adsList}">
							<option value="${entity.id}" ${entity.id==param.adsId?"selected":"" }>${entity.name}</option>
						</c:forEach>
					</select>						
				</div>
			</div>
			<div class="hr"></div>	 		
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2" >
			    <thead>
			        <tr>
			        	<th>首次统计日期</th>
			            <th>新增安装</th>
			            <th>1天留存</th>
			            <th>2天留存</th>
			            <th>3天留存</th>
			            <th>4天留存</th>
			            <th>5天留存</th>
			            <th>6天留存</th>
			            <th>7天留存</th>
			            <th>14天留存</th>
			            <th>30天留存</th>
			            <th>60天留存</th>
			        </tr>
			    </thead>
			    <tbody>
			    <c:set var="activateCount" value="0"/>
			    <c:set var="day1Count" value="0"/>
			    <c:set var="day2Count" value="0"/>
			    <c:set var="day3Count" value="0"/>
			    <c:set var="day4Count" value="0"/>
			    <c:set var="day5Count" value="0"/>
			    <c:set var="day6Count" value="0"/>
			    <c:set var="day7Count" value="0"/>
			    <c:set var="day14Count" value="0"/>
			    <c:set var="day30Count" value="0"/>
			    <c:set var="day60Count" value="0"/>
			    <c:forEach var="entity" items="${list}">
			    	 <c:set var="activateCount" value="${activateCount+entity.activateCount}"/>
			    	 <c:set var="day1Count" value="${day1Count+entity.day1Count}"/>
			    	 <c:set var="day2Count" value="${day2Count+entity.day2Count}"/>
			    	 <c:set var="day3Count" value="${day3Count+entity.day3Count}"/>
			    	 <c:set var="day4Count" value="${day4Count+entity.day4Count}"/>
			    	 <c:set var="day5Count" value="${day5Count+entity.day5Count}"/>
			    	 <c:set var="day6Count" value="${day6Count+entity.day6Count}"/>
			    	 <c:set var="day7Count" value="${day7Count+entity.day7Count}"/>
			    	 <c:set var="day14Count" value="${day14Count+entity.day14Count}"/>
			    	 <c:set var="day30Count" value="${day30Count+entity.day30Count}"/>
			    	 <c:set var="day60Count" value="${day60Count+entity.day60Count}"/>
			    	 <tr class="odd gradeX">
			    	 	<td> ${entity.activateDate}</td>
			    	 	<td>${entity.activateCount}</td>
			    	 	<td>${entity.day1Count}</td>
			    	 	<td>${entity.day2Count}</td>
			    	 	<td>${entity.day3Count}</td>
			    	 	<td>${entity.day4Count}</td>
			    	 	<td>${entity.day5Count}</td>
			    	 	<td>${entity.day6Count}</td>
			    	 	<td>${entity.day7Count}</td>
			    	 	<td>${entity.day14Count}</td>
			    	 	<td>${entity.day30Count}</td>
			    	 	<td>${entity.day60Count}</td>
			    	 </tr>
			    </c:forEach>
 					<tr class="odd gradeX">
			    	 	<th colspan="1">总计</th>
			    	 	<th>${activateCount}</th>
			    	 	<th>${day1Count}</th>
			    	 	<th>${day2Count}</th>
			    	 	<th>${day3Count}</th>
			    	 	<th>${day4Count}</th>
			    	 	<th>${day5Count}</th>
			    	 	<th>${day6Count}</th>
			    	 	<th>${day7Count}</th>
			    	 	<th>${day14Count}</th>
			    	 	<th>${day30Count}</th>
			    	 	<th>${day60Count}</th>
			    	 </tr>			    
			    </tbody>
			</table>
		</div>
	</div>
</div>
<script>
$(function() {
	 var idList=[ "fromDate","toDate","adsId"];
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
	