<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="banner_enabled">功能启用</label>
	<div class="controls">
		<select class="span9" id="banner_enabled" name="banner_enabled">
				<option value="0">否</option>
				<option value="1" ${banner.enabled?"selected":""}>是</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="banner_banner_startHour">开始时间</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_startHour" name="banner_startHour" value="${banner.startHour>0?banner.startHour:7}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="banner_endHour">结束时间</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_endHour" name="banner_endHour" value="${banner.endHour>0?banner.endHour:23}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="banner_interval">显示间隔(秒)</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_interval" name="banner_interval" value="${banner.interval>0?banner.interval:7200}" />
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="banner_maxCount">显示次数</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_maxCount" name="banner_maxCount" value="${banner.maxCount>0?banner.maxCount:5}" />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="banner_whitePackages">包名白名单</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_whitePackages" name="banner_whitePackages" value="${banner.whitePackages}" />
	</div>
</div>		

<div class="control-group">
	<label class="control-label" for="banner_blackPackages">包名黑名单</label>
	<div class="controls">
		<input class="span9" type="text" id="banner_blackPackages" name="banner_blackPackages" value="${banner.blackPackages}" />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="banner_autoExit">自动隐藏</label>
	<div class="controls">
		<select class="span9" id="banner_autoExit" name="banner_autoExit">
				<option value="0">否</option>
				<option value="1" ${banner.autoExit?"selected":""}>是</option>
		</select>
	</div>
</div>	
	