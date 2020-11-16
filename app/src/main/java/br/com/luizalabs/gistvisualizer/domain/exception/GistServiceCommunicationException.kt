package br.com.luizalabs.gistvisualizer.domain.exception

import java.lang.Exception

class GistServiceCommunicationException(
    private val msg: String? = ""
): Exception(msg)