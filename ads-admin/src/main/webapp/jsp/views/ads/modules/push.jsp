<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="push_enabled">功能启用</label>
	<div class="controls">
		<select class="span9" id="push_enabled" name="push_enabled">
				<option value="0">否</option>
				<option value="1" ${push.enabled?"selected":""}>是</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="push_startHour">开始时间</label>
	<div class="controls">
		<input class="span9" type="text" id="push_startHour" name="push_startHour" value="${push.startHour>0?push.startHour:7}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="push_endHour">结束时间</label>
	<div class="controls">
		<input class="span9" type="text" id="push_endHour" name="push_endHour" value="${push.endHour>0?push.endHour:23}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="push_interval">显示间隔(秒)</label>
	<div class="controls">
		<input class="span9" type="text" id="push_interval" name="push_interval" value="${push.interval>0?push.interval:7200}" />
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="push_maxCount">显示次数</label>
	<div class="controls">
		<input class="span9" type="text" id="push_maxCount" name="push_maxCount" value="${push.maxCount>0?push.maxCount:5}" />
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="push_cancelable">可以取消</label>
	<div class="controls">
		<select class="span9" id="push_cancelable" name="push_cancelable">
				<option value="0">否</option>
				<option value="1" ${push.cancelable?"selected":""}>是</option>
		</select>
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="push_enableSound">声音通知</label>
	<div class="controls">
		<select class="span9" id="push_enableSound" name="push_enableSound">
				<option value="0">关闭</option>
				<option value="1" ${push.enableSound?"selected":""}>开通</option>
		</select>
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="push_enableVibrate">震动通知</label>
	<div class="controls">
		<select class="span9" id="push_enableVibrate" name="push_enableVibrate">
				<option value="0">关闭</option>
				<option value="1" ${push.enableVibrate?"selected":""}>开通</option>
		</select>
	</div>
</div>		