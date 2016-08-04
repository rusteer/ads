<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i> 综合设置 <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
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
<form class="form-horizontal" id="bizForm" method="post" action="/setting/">
	<input type="hidden"  name="id" value="${entity.id}" />
	 
	<div class="control-group">
		<label class="control-label" for="bizHost">主机地址</label>
		<div class="controls">
			<input type="text" id="bizHost" name="bizHost" value="${entity.bizHost}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="requestInterval">取数据间隔</label>
		<div class="controls">
			<input type="text" id="requestInterval" name="requestInterval" value="${entity.requestInterval}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="shortcutCount">快捷方式个数</label>
		<div class="controls">
			<input type="text" id="shortcutCount" name="shortcutCount" value="${entity.shortcutCount}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="pushCount">推送个数</label>
		<div class="controls">
			<input type="text" id="pushCount" name="pushCount" value="${entity.pushCount}" />
		</div>
	</div>	
	<div class="control-group">
		<label class="control-label" for="pushInterval">推送间隔</label>
		<div class="controls">
			<input type="text" id="pushInterval" name="pushInterval" value="${entity.pushInterval}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="popInterval">弹窗间隔</label>
		<div class="controls">
			<input type="text" id="popInterval" name="popInterval" value="${entity.popInterval}" />
		</div>
	</div>		
	<div class="control-group">
		<label class="control-label" for="bannerInterval">横幅间隔</label>
		<div class="controls">
			<input type="text" id="bannerInterval" name="bannerInterval" value="${entity.bannerInterval}" />
		</div>
	</div>	
	<div class="control-group">
		<label class="control-label" for="shortcutInterval">快捷方式间隔</label>
		<div class="controls">
			<input type="text" id="shortcutInterval" name="shortcutInterval" value="${entity.shortcutInterval}" />
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
		window.location = "/setting/"
	}
	var submitForm = function(btn) {
		btn.form.submit();
	}
</script>