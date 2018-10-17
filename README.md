# Kotlin SVG DSL

I had a microservice that needed to offer up a small graph on an HTML page to a large audience rapidly. 
This was a DevOps requirement and I didn't want to own an involved Javascript solution. Spitting out a
half page of [inline SVG](https://www.w3schools.com/html/html5_svg.asp) text from the server seemed 
like the way to go.

This DSL lets you generate simple SVG from [Kotlin](https://kotlinlang.org/) very simply.

## Example

```kotlin
        val svg = svg {
            width = 200
            height = 200
            rect {
                title {
                    body = "A Blue Rectangle"
                }
                x = 50
                y = 50
                width = 20
                height = 10
                fill = "blue"
            }
            text {
                body = "label"
                x = 100
                y = 100
            }
            circle {
                cx = 100
                cy = 150
                r = 20
                fill = "red"
            }
            line {
                x1 = 0
                y1 = 0
                x2 = 40
                y2 = 40
                strokeWidth = 3
                stroke = "black"
            }
        }

        val sb = StringBuilder()
        svg.render(sb)
        System.out.println("<html><body>$sb</body></html>")
```

## See Also

- [License](LICENSE.md)
- [Javadoc](https://nwillc.github.io/ksvg/javadoc)

-----
[![license](https://img.shields.io/github/license/nwillc/ksvg.svg)](https://tldrlegal.com/license/-isc-license)
[![Travis](https://img.shields.io/travis/nwillc/ksvg.svg)](https://travis-ci.org/nwillc/ksvg)
[![Download](https://api.bintray.com/packages/nwillc/maven/ksvg/images/download.svg)](https://bintray.com/nwillc/maven/ksvg/_latestVersion)
