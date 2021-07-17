# Ti.APNG

apng Titanium module for Android using <a href="https://github.com/oupson/Kapng-Android">https://github.com/oupson/Kapng-Android</a>


## Installation

```
<modules>
    <module>ti.apgn</module>
</modules>
```

## Usage

```javascript
var win = Ti.UI.createWindow({
	title: "apng",
	backgroundColor: '#fff'
});

var img = Ti.UI.createImageView({
	image: "/appicon.png"
});
win.add(img);

var file = Ti.Filesystem.getFile(Ti.Filesystem.resourcesDirectory, "apng.png");
var APNG = require("ti.apng");
var apngView = APNG.createImageView({
	image: file,
	// image: "https://upload.wikimedia.org/wikipedia/commons/1/14/Animated_PNG_example_bouncing_beach_ball.png",
	width: 100,
	height: 100
});

apngView.addEventListener("click", function() {
	if (apngView.playing) {
		apngView.stop();
	} else {
		apngView.start();
	}
})

win.add(apngView);
win.open();
```


## Methods

<table>
<thead>
<tr>
<th>method</th>
<th>description</th>
</tr>
</thead>
<tr>
<td>start</td>
<td>starts the animation</td>
</tr>
<tr>
<td>stop</td>
<td>stops the animation</td>
</tr>
</table>

## Properties

<table>
<thead>
<tr>
<th>name</th>
<th>description</th>
</tr>
</thead>
<tr>
<td>playing</td>
<td>returns true/false</td>
</tr>
</table>



## Author

* Michael Gangolf (<a href="https://github.com/m1ga">@MichaelGangolf</a> / <a href="https://www.migaweb.de">Web</a>)
