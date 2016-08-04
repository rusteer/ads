<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i> CP管理 <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
		<li><a href="/cp/">CP列表</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
	</ul>
</div>
<c:if test="${param.saveSuccess==true}">
	<div class="alert alert-block alert-success">
		<button type="button" class="close" data-dismiss="alert">
			<i class="icon-remove"></i>
		</button>
		<i class="icon-ok green"></i> 保存成功!
	</div>
</c:if>
<c:if test="${saveSuccess==false}">
	<div class="alert alert-block alert-error">
		<button type="button" class="close" data-dismiss="alert">
			<i class="icon-remove"></i>
		</button>
		<i class="icon-remove"></i> 保存失败! ${errorMessage}
	</div>
</c:if>
<hr/>
<form class="form-horizontal" id="bizForm" method="post" action="/cp/">
	<div class="control-group">
		<label class="control-label" for="id">ID</label>
		<div class="controls">
			<input type="text" placeholder="ID" id="id" name="id" value="${entity.id}" readonly />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="name">名称</label>
		<div class="controls">
			<input type="text" placeholder="名称" id="name" name="name" value="${entity.name}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="loginName">登录名</label>
		<div class="controls">
			<input type="text" placeholder="登录名" id="loginName" name="loginName" value="${entity.loginName}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="loginPassword">登录密码</label>
		<div class="controls">
			<input type="text" placeholder="登录密码" id="loginPassword" name="loginPassword" value="${entity.loginPassword}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="deviceDailyLimit">用户日限</label>
		<div class="controls">
			<input type="text" placeholder="用户日限" id="deviceDailyLimit" name="deviceDailyLimit" value="${entity.deviceDailyLimit}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="discountRate">扣量百分比</label>
		<div class="controls">
			<input type="text" placeholder="扣量百分比" id="discountRate" name="discountRate" value="${entity.discountRate}" />
		</div>
	</div>	
	<div class="form-actions">
		<button class="btn btn-info" type="button" onclick="submitForm(this)">
			<i class="icon-ok bigger-110"></i> 保存
		</button>
		&nbsp; &nbsp; &nbsp;
		<button class="btn" type="button" onclick="goBack()">
			<i class="icon-backward bigger-110"></i> 返回
		</button>
	</div>
</form>
<script type="text/javascript">
	var goBack = function() {
		window.history.back();
	}
	var submitForm = function(btn) {
		btn.form.submit();
	}
</script>