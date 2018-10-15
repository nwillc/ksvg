# Kotlin SVG DSL

I had a microservice that needed to offer up a small graph on an HTML page to a large audience rapidly. 
This was a DevOps requirement and I didn't want to own an involved Javascript solution. Spitting out a
half page of [inline SVG](https://www.w3schools.com/html/html5_svg.asp) text from the server seemed 
like the way to go.

This DSL lets you generate simple SVG from [Kotlin](https://kotlinlang.org/) very simply.

## Example

```kotlin
   val svg = svg {
        rect {
            width = 20
            height = 10
        }
   }
   
   val sb = StringBuffer()
   svg.render(sb)
```

-----
[![license](https://img.shields.io/github/license/nwillc/ksvg.svg)](https://tldrlegal.com/license/-isc-license)
[![Travis](https://img.shields.io/travis/nwillc/ksvg.svg)](https://travis-ci.org/nwillc/ksvg)
[![Download](https://api.bintray.com/packages/nwillc/maven/ksvg/images/download.svg)](https://bintray.com/nwillc/maven/ksvg/_latestVersion)
