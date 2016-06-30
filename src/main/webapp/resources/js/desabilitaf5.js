function disableF5(e) { if (e.keyCode == 116) e.preventDefault(); };
$(document).on("keydown", disableF5);
