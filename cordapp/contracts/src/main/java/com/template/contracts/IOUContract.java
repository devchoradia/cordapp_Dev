package com.template.contracts;

import com.template.states.TemplateState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;

import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import static net.corda.core.contracts.ContractsDSL.requireThat;
import java.util.List;



public class IOUContract implements Contract {

    public static final String ID = "com.template.contracts.TemplateContract";

    @Override
    public void verify(LedgerTransaction tx) {
        CommandWithParties<IOUContract.create> command = requireSingleCommand(tx.getCommands(), IOUContract.create.class);
        if(!tx.getInputs().isEmpty())
            throw new IllegalArgumentException("No Input should be consumed");
        if(tx.getOutputs().size()==1)
            throw new IllegalArgumentException("Only one output");

        final IOUState output = tx.outputsOfType(IOUState.class).get(0);

        final party exporter = output.getExporter();
        final party importer = output.getImporter();

        if(output.getValue()<=0)
            throw new IllegalArgumentException("IOU should have a value and  be positive");

        if(exporter.equals(importer))
            throw new IllegalArgumentException("Value should no be same");


        // check signatures also..


    }

}
