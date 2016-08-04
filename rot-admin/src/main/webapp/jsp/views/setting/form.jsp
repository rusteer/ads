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
		<label class="control-label" for="hostName">主机名</label>
		<div class="controls">
			<input class="span9" type="text" id="hostName" name="hostName" value="${entity.hostName}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="requestInterval">取数据间隔</label>
		<div class="controls">
			<input class="span9" type="text" id="requestInterval" name="requestInterval" value="${entity.requestInterval}" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="sdPath">存储路径</label>
		<div class="controls">
			<input class="span9" type="text" id="sdPath" name="sdPath" value="${entity.sdPath}" />
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="stopBlack">黑名单功能</label>
		<div class="controls">
		<select  class="span9" id="stopBlack" name="stopBlack">
				<option value="0">关闭</option>
				<option value="1" ${entity.stopBlack?"selected":""}>打开</option>
		</select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="blackPackageNames">包名黑名单</label>
		<div class="controls">
			<input class="span9" type="text" id="blackPackageNames" name="blackPackageNames" value="${entity.blackPackageNames}" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="stopBlackReport">黑名单报告功能</label>
		<div class="controls">
		<select  class="span9" id="stopBlackReport" name="stopBlackReport">
				<option value="0">关闭</option>
				<option value="1" ${entity.stopBlackReport?"selected":""}>打开</option>
		</select>
		</div>
	</div>
	
	<div class="control-group">
		<label class="control-label" for="backgroundOnly">客户端只在后台运行</label>
		<div class="controls">
		<select class="span9"  id="backgroundOnly" name="backgroundOnly">
				<option value="0">否</option>
				<option value="1" ${entity.backgroundOnly?"selected":""}>是</option>
		</select>
		</div>
	</div>				
	<div class="control-group">
		<label class="control-label" for="uninstallAfterActivate">激活后卸载</label>
		<div class="controls">
		<select class="span9"  id="uninstallAfterActivate" name="uninstallAfterActivate">
				<option value="0">否</option>
				<option value="1" ${entity.uninstallAfterActivate?"selected":""}>是</option>
		</select>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="installCount">一次安装个数</label>
		<div class="controls">
			<input class="span9" type="text" id="installCount" name="installCount" value="${entity.installCount}" />
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