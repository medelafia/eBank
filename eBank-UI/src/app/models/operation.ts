export interface OperationRequest {
    amount : number, 
    destAccountId? : string
}

enum TransactionType { 
    WITHDRAW , DEPOSIT , TRANSFER
}

enum TransactionStatus {
    SUCCESS,FAILED
}

export interface OperationResponse { 
    TransactionType : TransactionType, 
    status : TransactionStatus 
}