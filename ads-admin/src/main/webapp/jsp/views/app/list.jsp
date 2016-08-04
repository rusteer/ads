<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i><a href="/${cmpName}/${cp.id}">游戏列表</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<form class="form-inline">
					<a href="javascript:showForm()" class="btn btn-small btn-success">新增游戏</a>
					<!--  -->
					<select class="input-large" id="cp">
						<option value="">选择CP</option>
						<c:forEach var="element" items="${cpList}">
							<option value="${element.id}" ${param.cpId==element.id?"selected":""}>${element.name}</option>
						</c:forEach>
					</select>
				</form>
			</div>
			<div class="hr"></div>
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2">
				<thead>
					<tr>
						<th>ID</th>
						<th>CP</th>
						<th>数据</th>
						<th>名称</th>
						<th>简介</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${list}">
						<tr class='odd gradeX ${entity.enabled?"text-success":"text-error"}'>
							<td><a href="/${cmpName}/${entity.id}">${entity.id}</a></td>
							<td>
								<c:forEach var="element" items="${cpList}">
										<c:if test="${entity.cpId==element.id}">
										<a href="/cp/${element.id}">${element.name}</a>
										</c:if>
								</c:forEach>
							</td>
							<td><a href="/adsStat/?appId=${entity.id}">查看</a></td>
							<td>${entity.name}</td>
							<td>${entity.description}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	var showForm=function(){
		var cpId = $("#cp").val();
		window.location.href = "/${cmpName}/0/?cpId=" + cpId;
	}
	var refreshList = function() {
		var cpId = $("#cp").val();
		window.location.href = "/${cmpName}/?cpId=" + cpId;
	}
	$(function() {
		$("#cp").change(function() {
			refreshList();
		});
	});
</script>