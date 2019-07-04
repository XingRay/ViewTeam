# ViewTeam
help for showing or hiding view on android

## How to 
To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` groovy

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

### Step 2. Add the dependency

``` groovy

	dependencies {
	        implementation 'com.github.XingRay:ViewTeam:0.2.0'
	}

```

### step 3. init viewTeam

```kotlin

viewTeam = ViewTeam.of(container)
    .inflate(1, R.layout.team_01)
    .add(2, Button(applicationContext).apply {
        text = "team02"
    })
    .merge(3, R.layout.team_linearlayout)

```
### step 4. switch team 
```kotlin

viewTeam.setTeam(teamId)

```

more usage see sample
