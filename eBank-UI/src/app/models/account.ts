import { User } from "./user";

export enum AccountType { 
    SAVING_ACCOUNT= "SAVING_ACCOUNT", 
    CURRENT_ACCOUNT = "CURRENT_ACCOUNT" ,
    CHECKING_ACCOUNT = "CHECKING_ACCOUNT",
    FIXED_ACCOUNT = "FIXED_ACCOUNT"
}
export enum AccountStatus { 
    ACTIVE = "ACTIVE", 
    INACTIVE = "INACTIVE"
}
export interface Account {
    accountId? : string 
    currency : string 
    accountType : AccountType
    balance : number
    createdAt? : Date 
    status : AccountStatus

    user : User
}
