package com.leptron.leptronquiz.util

object StringUtils {

    private val htmlEscape = arrayOf(arrayOf("&lt;", "<"), arrayOf("&gt;", ">"), arrayOf("&amp;", "&"), arrayOf("&quot;", "\""), arrayOf("&agrave;", "à"),
        arrayOf("&Agrave;", "À"), arrayOf("&acirc;", "â"), arrayOf("&auml;", "ä"), arrayOf("&Auml;", "Ä"), arrayOf("&Acirc;", "Â"), arrayOf("&aring;", "å"),
        arrayOf("&Aring;", "Å"), arrayOf("&aelig;", "æ"), arrayOf("&AElig;", "Æ"), arrayOf("&ccedil;", "ç"), arrayOf("&Ccedil;", "Ç"), arrayOf("&eacute;", "é"),
        arrayOf("&Eacute;", "É"), arrayOf("&egrave;", "è"), arrayOf("&Egrave;", "È"), arrayOf("&ecirc;", "ê"), arrayOf("&Ecirc;", "Ê"), arrayOf("&euml;", "ë"),
        arrayOf("&Euml;", "Ë"), arrayOf("&iuml;", "ï"), arrayOf("&Iuml;", "Ï"), arrayOf("&ocirc;", "ô"), arrayOf("&Ocirc;", "Ô"), arrayOf("&ouml;", "ö"),
        arrayOf("&Ouml;", "Ö"), arrayOf("&oslash;", "ø"), arrayOf("&Oslash;", "Ø"), arrayOf("&szlig;", "ß"), arrayOf("&ugrave;", "ù"), arrayOf("&Ugrave;", "Ù"),
        arrayOf("&ucirc;", "û"), arrayOf("&Ucirc;", "Û"), arrayOf("&uuml;", "ü"), arrayOf("&Uuml;", "Ü"), arrayOf("&nbsp;", " "), arrayOf("&copy;", "\u00a9"),
        arrayOf("&reg;", "\u00ae"), arrayOf("&euro;", "\u20a0"), arrayOf("&#039;", "'"))

    fun unescapeHTML(sParameter: String, start: Int): String {
        var s = sParameter
        val i: Int
        val j: Int
        var k: Int

        i = s.indexOf("&", start)
        if (i > -1) {
            j = s.indexOf(";", i)
            /*
           we don't want to start from the beginning
           the next time, to handle the case of the &
           thanks to Pieter Hertogh for the bug fix!
        */
            if (j > i) {
                // ok this is not most optimized way to
                // do it, a StringBuffer would be better,
                // this is left as an exercise to the reader!
                val temp = s.substring(i, j + 1)
                // search in htmlEscape[][] if temp is there
                k = 0
                while (k < htmlEscape.size) {
                    if (htmlEscape[k][0] == temp)
                        break
                    else
                        k++
                }
                if (k < htmlEscape.size) {
                    s = (s.substring(0, i)
                            + htmlEscape[k][1] + s.substring(j + 1))
                    return unescapeHTML(s, i) // recursive call
                }
            }
        }
        return s
    }
}