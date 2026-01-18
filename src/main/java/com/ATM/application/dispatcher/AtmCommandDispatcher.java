package com.ATM.application.dispatcher;

import com.ATM.application.command.AtmCommand;
import com.ATM.application.command.AtmContext;
import com.ATM.application.command.AtmOperation;
import com.ATM.application.command.AtmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AtmCommandDispatcher {
    private final List<AtmCommand> commands;

    public AtmResponse dispatch(AtmOperation operation, AtmContext context) {
        return commands.stream()
                .filter(c -> c.supports(operation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation"))
                .execute(context);
    }
}
