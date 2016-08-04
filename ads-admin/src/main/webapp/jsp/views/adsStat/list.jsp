<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i>数据同步 <span class="divider"> <i class="icon-angle-right arrow-icon"></i> </span></li>
		<li><a href="/${cmpName}/">代码同步</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i> </span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<div class="btn-group">
					<input type="text" class="input-small" id="fromDate" value="${fromDate}" placeholder="起始日期"> 
					<input type="text" class="input-small" id="toDate" value="${toDate}" placeholder="结束日期"> 
					<select class="input-large" id="groupName">
						<option value="date" ${"date"==groupName?"selected":"" }>日期分组</option>
						<option value="ads" ${"ads"==groupName?"selected":"" }>广告分组</option>
						<option value="app" ${"app"==groupName?"selected":"" }>游戏分组</option>
						<option value="channel" ${"channel"==groupName?"selected":"" }>渠道分组</option>
					</select>									
					<select class="input-large" id="advertiserId">
						<option value="">--广告主--</option>
						<c:forEach var="entity" items="${advertiserList}">
							<option value="${entity.id}" ${entity.id==param.advertiserId?"selected":"" }>${entity.name}</option>
						</c:forEach>
					</select>
					<select class="input-large" id="adsId">
						<option value="">--广告--</option>
						<c:forEach var="entity" items="${adsList}">
							<option value="${entity.id}" ${entity.id==param.adsId?"selected":"" }>${entity.name}</option>
						</c:forEach>
					</select>						
					<select class="input-large" id="cpId">
						<option value="">--CP--</option>
						<c:forEach var="entity" items="${cpList}">
							<option value="${entity.id}" ${entity.id==param.cpId?"selected":"" }>${entity.name}</option>
						</c:forEach>
					</select>
					<select class="input-large" id="channelId">
						<option value="">--渠道--</option>
						<c:forEach var="entity" items="${channelList}">
							<option value="${entity.channelId}" ${entity.channelId==param.channelId?"selected":"" }>${entity.channelId}</option>
						</c:forEach>
					</select>							
					<select class="input-large" id="appId">
						<option value="">--游戏--</option>
						<c:forEach var="entity" items="${appList}">
							<option value="${entity.id}" ${entity.id==param.appId?"selected":"" }>${entity.name}</option>
						</c:forEach>
					</select>									
				</div>
			</div>
			<div class="hr"></div>	 		
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2" >
			    <thead>
			        <tr>
			        	<th>
			        		<c:if test="${groupName=='date'}">日期</c:if>
			        		<c:if test="${groupName=='ads'}">广告</c:if>
			        		<c:if test="${groupName=='app'}">游戏</c:if>
			        		<c:if test="${groupName=='channel'}">渠道</c:if>
			        	</th>
			            <th>横幅展示数</th>
			            <th>弹窗数</th>
			            <th>推送数</th>
			            <th>快捷方式展示数</th>
			            <th>安装数</th>
			            <th>金额(元)</th>
			        </tr>
			    </thead>
			    <tbody>
			    <c:set var="pushCount" value="0"/>
			    <c:set var="popCount" value="0"/>
			    <c:set var="bannerCount" value="0"/>
			    <c:set var="shortcutCount" value="0"/>
			    <c:set var="installCount" value="0"/>
			    <c:set var="earning" value="0"/>
			    <c:forEach var="entity" items="${list}">
			    	 <c:set var="pushCount" value="${pushCount+entity.pushCount}"/>
			    	 <c:set var="popCount" value="${popCount+entity.popCount}"/>
			    	 <c:set var="bannerCount" value="${bannerCount+entity.bannerCount}"/>
			    	 <c:set var="shortcutCount" value="${shortcutCount+entity.shortcutCount}"/>
			    	 <c:set var="installCount" value="${installCount+entity.installCount}"/>
			    	 <c:set var="earning" value="${earning+entity.earning}"/>
			    	 <tr class="odd gradeX">
			    	 	<td>
							<c:if test="${groupName=='date'}">${entity.statDate}</c:if>
			        		<c:if test="${groupName=='ads'}">
			        			<c:forEach var="ads" items="${adsList}">
			        				<c:if test="${ads.id==entity.adsId}">
			        					<a href="/ads/${ads.id}">${ads.name}</a>
			        				</c:if>
								</c:forEach>
			        		</c:if>
			        		<c:if test="${groupName=='app'}">
								<c:forEach var="app" items="${appList}">
			        				<c:if test="${app.id==entity.appId}">
			        					<a href="/app/${app.id}">${app.name}</a>
			        				</c:if>
								</c:forEach>			        		
			        		</c:if>
			        		<c:if test="${groupName=='channel'}">${entity.channelId}</c:if>			    	 	
			    	 	</td>
			    	 	<td>${entity.bannerCount}</td>
			    	 	<td>${entity.popCount}</td>
			    	 	<td>${entity.pushCount}</td>
			    	 	<td>${entity.shortcutCount}</td>
			    	 	<td>${entity.installCount}</td>
			    	 	<td><fmt:formatNumber value="${entity.earning/100}" pattern="##" /></td>
			    	 </tr>
			    </c:forEach>
 					<tr class="odd gradeX">
			    	 	<th colspan="1">总计</th>
			    	 	<th>${bannerCount}</th>
			    	 	<th>${popCount}</th>
			    	 	<th>${pushCount}</th>
			    	 	<th>${shortcutCount}</th>
			    	 	<th>${installCount}</th>
			    	 	<th><fmt:formatNumber value="${earning/100}" pattern="##" /></th>
			    	 </tr>			    
			    </tbody>
			</table>
		</div>
	</div>
</div>
<script>
$(function() {
	 var idList=["groupName","fromDate","toDate","cpId", "advertiserId", "appId", "adsId", "channelId"];
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
	