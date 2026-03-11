```html
<!DOCTYPE html>
<html>
<head>
<title>Hospital Patient Feedback System</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
body{
font-family:Arial;
margin:0;
background:#f5f7fb;
}

header{
background:#0b5ed7;
color:white;
padding:15px;
text-align:center;
font-size:22px;
}

nav{
background:#083b8a;
display:flex;
}

nav button{
flex:1;
padding:12px;
border:none;
background:none;
color:white;
cursor:pointer;
}

nav button:hover{
background:#0b5ed7;
}

section{
display:none;
padding:20px;
}

section.active{
display:block;
}

.card{
background:white;
padding:20px;
border-radius:10px;
margin-bottom:20px;
box-shadow:0 2px 8px rgba(0,0,0,.1);
}

input,select,textarea{
width:100%;
padding:10px;
margin:8px 0;
border-radius:6px;
border:1px solid #ccc;
}

button.primary{
background:#0b5ed7;
color:white;
border:none;
padding:10px 18px;
cursor:pointer;
border-radius:6px;
}

.center{
display:flex;
justify-content:center;
align-items:center;
height:100vh;
}
</style>
</head>

<body>

<!-- LOGIN PAGE -->

<div id="loginPage" class="center">
<div class="card" style="width:300px">

<h2>Login</h2>

<input id="username" placeholder="Username">
<input id="password" type="password" placeholder="Password">

<button class="primary" onclick="login()">Login</button>

<p id="loginError" style="color:red"></p>

</div>
</div>


<!-- MAIN SYSTEM -->

<div id="mainSystem" style="display:none">

<header>Hospital Patient Feedback System</header>

<nav>
<button onclick="openTab('dashboard')">Dashboard</button>
<button onclick="openTab('feedback')">Feedback Form</button>
<button onclick="openTab('reviews')">Reviews</button>
<button onclick="logout()">Logout</button>
</nav>


<!-- DASHBOARD -->

<section id="dashboard" class="active">

<div class="card">
<h3>Statistics</h3>
<p>Total Feedback: <span id="total">0</span></p>
<p>Average Rating: <span id="avg">0</span></p>
</div>

<div class="card">
<canvas id="chart"></canvas>
</div>

</section>


<!-- FEEDBACK FORM -->

<section id="feedback">

<div class="card">

<h3>Patient Feedback</h3>

<input id="name" placeholder="Patient Name">

<select id="dept">
<option>General</option>
<option>Emergency</option>
<option>Pharmacy</option>
<option>Billing</option>
</select>

<select id="rating">
<option value="5">Excellent</option>
<option value="4">Good</option>
<option value="3">Average</option>
<option value="2">Poor</option>
<option value="1">Very Poor</option>
</select>

<textarea id="comment" placeholder="Comments"></textarea>

<button class="primary" type="button" onclick="addFeedback()">Submit Feedback</button>

</div>

</section>


<!-- REVIEWS -->

<section id="reviews">

<div class="card">
<h3>Patient Reviews</h3>
<ul id="reviewList"></ul>
</div>

</section>

</div>


<script>

/* GLOBAL VARIABLES */

let feedbackList = [];


/* LOGIN */

function login(){

let user=document.getElementById("username").value;
let pass=document.getElementById("password").value;

if(user==="admin" && pass==="1234"){

document.getElementById("loginPage").style.display="none";
document.getElementById("mainSystem").style.display="block";

loadFeedback();

}else{

document.getElementById("loginError").textContent="Invalid login";

}

}

function logout(){
location.reload();
}


/* NAVIGATION */

function openTab(id){

document.querySelectorAll("section").forEach(sec=>{
sec.classList.remove("active");
});

document.getElementById(id).classList.add("active");

}


/* ADD FEEDBACK */

function addFeedback(){

let name=document.getElementById("name").value.trim();
let dept=document.getElementById("dept").value;
let rating=Number(document.getElementById("rating").value);
let comment=document.getElementById("comment").value.trim();

if(name===""){
alert("Enter patient name");
return;
}

if(comment===""){
comment="No comment";
}

let obj={
name:name,
dept:dept,
rating:rating,
comment:comment
};

feedbackList.push(obj);

/* SAVE TO LOCAL STORAGE */

localStorage.setItem("hospital_feedback", JSON.stringify(feedbackList));

updateDashboard();
renderReviews();

/* CLEAR FORM */

document.getElementById("name").value="";
document.getElementById("comment").value="";

alert("Feedback submitted successfully!");

}


/* LOAD SAVED DATA */

function loadFeedback(){

let data=localStorage.getItem("hospital_feedback");

if(data){
feedbackList=JSON.parse(data);
}

updateDashboard();
renderReviews();

}


/* DASHBOARD */

function updateDashboard(){

let total=feedbackList.length;
document.getElementById("total").textContent=total;

let avg=0;

if(total>0){

let sum=feedbackList.reduce((a,b)=>a+b.rating,0);
avg=(sum/total).toFixed(2);

}

document.getElementById("avg").textContent=avg;

updateChart();

}


/* REVIEWS */

function renderReviews(){

let list=document.getElementById("reviewList");
list.innerHTML="";

feedbackList.slice().reverse().forEach(f=>{

let li=document.createElement("li");

li.textContent=
f.name+" ("+f.dept+") : "+f.rating+"★ - "+f.comment;

list.appendChild(li);

});

}


/* CHART */

let chart;

window.onload=function(){

chart=new Chart(
document.getElementById("chart"),
{
type:"bar",
data:{
labels:["1","2","3","4","5"],
datasets:[{
label:"Ratings",
data:[0,0,0,0,0]
}]
}
}
);

};


/* UPDATE CHART */

function updateChart(){

if(!chart) return;

let counts=[0,0,0,0,0];

feedbackList.forEach(f=>{
counts[f.rating-1]++;
});

chart.data.datasets[0].data=counts;

chart.update();

}

</script>

</body>
</html>
```
