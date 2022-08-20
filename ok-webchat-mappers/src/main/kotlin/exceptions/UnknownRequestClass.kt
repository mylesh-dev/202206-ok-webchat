package net.murzikov.webchat.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>): RuntimeException("Class $clazz cannot be mapped to ChatContext")
