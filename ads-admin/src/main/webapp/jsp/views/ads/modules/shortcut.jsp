<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="control-group">
	<label class="control-label" for="shortcut_enabled">功能启用</label>
	<div class="controls">
		<select class="span9" id="shortcut_enabled" name="shortcut_enabled">
				<option value="0">否</option>
				<option value="1" ${shortcut.enabled?"selected":""}>是</option>
		</select>
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="shortcut_startHour">开始时间</label>
	<div class="controls">
		<input class="span9" type="text" id="shortcut_startHour" name="shortcut_startHour" value="${shortcut.startHour>0?shortcut.startHour:7}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="shortcut_endHour">结束时间</label>
	<div class="controls">
		<input class="span9" type="text" id="shortcut_endHour" name="shortcut_endHour" value="${shortcut.endHour>0?shortcut.endHour:23}" />
	</div>
</div>	
<div class="control-group">
	<label class="control-label" for="shortcut_interval">显示间隔(秒)</label>
	<div class="controls">
		<input class="span9" type="text" id="shortcut_interval" name="shortcut_interval" value="${shortcut.interval>0?shortcut.interval:7200}" />
	</div>
</div>	

<div class="control-group">
	<label class="control-label" for="shortcut_maxCount">显示次数</label>
	<div class="controls">
		<input class="span9" type="text" id="shortcut_maxCount" name="shortcut_maxCount" value="${shortcut.maxCount>0?shortcut.maxCount:5}" />
	</div>
</div>


	