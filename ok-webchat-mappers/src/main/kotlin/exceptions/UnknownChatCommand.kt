package net.murzikov.webchat.mappers.exceptions

import net.murzikov.webchat.common.models.ChatCommand

class UnknownChatCommand(command: ChatCommand)
    : Throwable("Wrong command $command encountered when mapping on toTransport stage")
