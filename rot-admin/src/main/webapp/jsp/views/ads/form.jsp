<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i><a href="/${cmpName}/${cp.id}">${cp.name}</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
		<li><a href="/${cmpName}/">广告列表</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i></span></li>
		<li>广告编辑页面 </li>
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
<form class="form-horizontal" id="bizForm" method="post" action="/${cmpName}/">
		<ul class="nav nav-tabs" id="bizFormTab">
			<li><a href="#tab1" data-toggle="tab">基本属性</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane span10" id="tab1">
				 <jsp:include page="modules/basic.jsp" />			
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
	$(function() {
		$('#bizFormTab a:first').tab('show')
	});
	var goBack = function() {
		window.history.back();
	}
	var submitForm = function(btn) {
		btn.form.submit();
	}
</script>