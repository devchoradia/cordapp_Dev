package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.contracts.ContractState;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;

import javax.annotation.Signed;

@InitiatedBy(Initiator.class)
public class Responder extends FlowLogic<Void> {

    private FlowSession otherpartysession;

    public Responder(FlowSession counterpartySession) {
        this.counterpartySession = counterpartySession;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        class signTxFlow extends signTransactionFlow{

            public signTxFlow(@NotNull FlowSession otherSideSession) {
                super(otherSideSession);
            }

            protected void checkTransaction(@NotNull SignedTransaction stx) throws FlowsException{
                requireThat(require -> {
                    ContractState output = stx.getTx().getOutput().get(0).getData;
                })

                }            }
        }

        subFlow(new ReceiveFinalityFlow(counterpartySession, signedTransaction.getId()));
        return null;
    }
