<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i><a href="/${cmpName}/${cp.id}">广告列表</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<form class="form-inline">
					<a href="javascript:showForm()" class="btn btn-small btn-success">新增广告</a>
				</form>
			</div>
			<div class="hr"></div>
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2">
				<thead>
					<tr>
						<th>ID</th>
						<th>综合统计</th>
						<th>留存统计 </th>
						<th>名称</th>
						<th>包名</th>
						<th>下载地址</th>
						<th>开启统计功能</th>
						<th>开启安装功能</th>
						<th>开启激活功能</th>
						<th>激活方法</th>
						<th>激活组件</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${list}">
						<tr class='odd gradeX'>
							<td><a href="/${cmpName}/${entity.id}">${entity.id}</a></td>
							<td><a href="/summary/?adsId=${entity.id}">查看</a></td>
							<td><a href="/retention/?adsId=${entity.id}">查看</a></td>
							<td>${entity.name}</td>
							<td>${entity.packageName}</td>
							<td><c:if test="${not empty entity.downloadUrl}"><a href="${entity.downloadUrl}" target="_blank">下载</a></c:if></td>
							<td>${entity.enableStat?"打开":"关闭"}</td>
							<td>${entity.enableInstall?"打开":"关闭"}</td>
							<td>${entity.enableActivate?"打开":"关闭"}</td>
							<td>${entity.activateMethod==1?"Activity":"Service"}</td>
							<td>${entity.activateComponent}</td>
							<td>${entity.createTime}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	var showForm=function(){
		window.location.href = "/${cmpName}/0";
	}
	$(function() {
		//$("#advertiser").change(function() {
		//	refreshList();
		//});
	});
</script>