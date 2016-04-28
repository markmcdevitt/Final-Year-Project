<form action="${pageContext.request.contextPath}/yourallergy"
	method="get">
		<table class="table table-bordered table-striped"  style="width:30%">
			<thead>
				<tr>
					<th>Allergy</th>
					<th>Checkbox</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Nuts</td>
					<td><input id="nuts" name="nuts" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="nutsHidden" name="nuts" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Milk</td>
					<td><input id="milk" name="milk" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="milkHidden" name="milk" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Peanuts</td>
					<td><input id="peanuts" name="peanuts" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="peanutsHidden" name="peanuts" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Eggs</td>
					<td><input id="eggs" name="eggs" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> , <input
						id="eggsHidden" name="eggs" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Fish</td>
					<td><input id="fish" name="fish" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="fishHidden" name="fish" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Shellfish</td>
					<td><input id="shellfish" name="shellfish" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="shellfishHidden" name="shellfish" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Wheat</td>
					<td><input id="wheat" name="wheat" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="wheatHidden" name="wheat" type="hidden" value="no"></td>
				</tr>
				<tr>
					<td>Soy</td>
					<td><input id="soy" name="soy" type="checkbox"
						data-toggle="checkbox-x" data-enclosed-label="true"> <input
						id="soyHidden" name="soy" type="hidden" value="no"></td>
				</tr>
			</tbody>
		</table>
	<input type="submit" class="btn btn-primary btn-lg" value="Submit">
</form>
<script>
	if (document.getElementById("nuts").checked) {
		document.getElementById('nutsHidden').disabled = true;
	}
	if (document.getElementById("milk").checked) {
		document.getElementById('milkHidden').disabled = true;
	}
	if (document.getElementById("peanuts").checked) {
		document.getElementById('peanutsHidden').disabled = true;
	}
	if (document.getElementById("eggs").checked) {
		document.getElementById('eggsHidden').disabled = true;
	}
	if (document.getElementById("fish").checked) {
		document.getElementById('fishHidden').disabled = true;
	}
	if (document.getElementById("shellfish").checked) {
		document.getElementById('shellfishHidden').disabled = true;
	}
	if (document.getElementById("wheat").checked) {
		document.getElementById('wheatHidden').disabled = true;
	}
	if (document.getElementById("soy").checked) {
		document.getElementById('soyHidden').disabled = true;
	}
</script>
