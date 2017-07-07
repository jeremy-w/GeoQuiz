# GeoQuiz
*Jeremy W. Sherman, July 2017*

This is my version of the app developed across the first few chapters of the
[Big Nerd Ranch Android Programming Guide, 3 ed.](https://www.bignerdranch.com/books/android-programming/)

I worked the chapters in Kotlin, including challenges,
using Android Studio 3.0 Canary 5.

I deviated from the text in that I skipped the `landscape` layout variation
bit.

A key element of my solution is using the
[Kotlin Android Extensions](https://kotlinlang.org/docs/tutorials/android-plugin.html)
to simplify accessing inflated layout widgets.

If you hit a class-cast exception after hacking on your layout XML,
do a full rebuild, and all should be well. This seems to happen when you use
the same ID with a new widget type, like changing `@id/next_button` from
`Button` to `ImageButton`.
