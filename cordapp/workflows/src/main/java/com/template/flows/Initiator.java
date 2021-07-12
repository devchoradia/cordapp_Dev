package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.TemplateContract;
import com.template.states.TemplateState;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@InitiatingFlow
@StartableByRPC
public class Initiator implements FlowLogic<void>{
    private final int iouvalue;
    private final Party otherparty;

    private final ProgressTracker progressTracker = new ProgressTracker();
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    public Initiator(int iouvalue, Party otherparty) {
        this.iouvalue = iouvalue;
        this.otherparty = otherparty;
    }

    public Void call() throws FlowException{

        final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        final IOUState output = new IOUState(iouvalue,getOurIdentity(),otherparty);

        Command command = new Command<>(new IOUContract.Commands.Create(),Arrays.asList(getOurIdentity().getOwningKey(),otherparty.getOwningKey());

        final TransactionBuilder builder = new TransactionBuilder(notary);

        builder.addOutputState(output);
        builder.addCommand(command);
        builder.verify(getServiceHub());

        final SignedTransaction Signedtx = getServiceHub().signInitialTransaction(builder);

        FlowSession otherpartysession = initateFlow(otherparty);

        signedTransaction fullysignedtx =  subFlow(new CollectSignaturesFlow(signedtx,Arrays.asList(otherpartysession),CollectSignaturesFlow.tracker()))
        subFlow(new FinalityFlow(fullysignedtx , otherpartysession));


        return null;
    }
}


