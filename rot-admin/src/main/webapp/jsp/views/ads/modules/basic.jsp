<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="id">ID</label>
	<div class="controls">
		<input class="span9" type="text" id="id" name="id" value="${entity.id}" readonly />
	</div>
</div>	
 	
<div class="control-group">
	<label class="control-label" for="name">名称</label>
	<div class="controls">
		<input class="span9" type="text" id="name" name="name" value="${entity.name}" />
	</div>
</div>
 
 	
<div class="control-group">
	<label class="control-label" for="packageName">包名</label>
	<div class="controls">
		<input class="span9" type="text"  id="packageName" name="packageName" value="${entity.packageName}" />
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="downloadUrl">下载地址</label>
	<div class="controls">
		<input class="span9" type="text"  id="downloadUrl" name="downloadUrl" value="${entity.downloadUrl}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="enableStat">统计功能</label>
	<div class="controls">
		<select class="span9" id="enableStat" name="enableStat">
				<option value="0">关闭</option>
				<option value="1" ${entity.enableStat?"selected":""}>打开</option>
		</select>
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="enableStat">安装功能</label>
	<div class="controls">
		<select class="span9" id="enableInstall" name="enableInstall">
				<option value="0">关闭</option>
				<option value="1" ${entity.enableInstall?"selected":""}>打开</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="enableActivate">激活功能</label>
	<div class="controls">
		<select class="span9" id="enableActivate" name="enableActivate">
				<option value="0">关闭</option>
				<option value="1" ${entity.enableActivate?"selected":""}>打开</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="activateMethod">激活方法</label>
	<div class="controls">
		<select class="span9" id="activateMethod" name="activateMethod">
				<option value="1">Activity</option>
				<option value="2" ${entity.activateMethod==2?"selected":""}>Service</option>
		</select>
	</div>
</div> 

<div class="control-group">
	<label class="control-label" for="activateComponent">激活组件</label>
	<div class="controls">
		<input class="span9" type="text"  id="activateComponent" name="activateComponent" value="${entity.activateComponent}" />
	</div>
</div>	