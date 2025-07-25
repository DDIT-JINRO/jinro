/**
 * 
 */
function header(){
	
	const menuIcon = document.getElementById("menuToggle");
	const dropdown = document.getElementById("dropdownMenu");
	
	menuIcon.addEventListener("click",() => {
		dropdown.classList.toggle("hidden");
	});
	
	document.addEventListener("click",(event) => {
	      if (!dropdown.contains(event.target) && !menuIcon.contains(event.target)) {
	    	  dropdown.classList.add("hidden");
	      }
	});
}