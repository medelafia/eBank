import { Account, AccountStatus, AccountType } from '@/models/account';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { TabsModule } from 'primeng/tabs';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { User } from '@/models/user';
import { AccountServices } from '@/services/account-services/account-services';
import { UserServices } from '@/services/user-services/user-services';
import { DecimalPipe, UpperCasePipe } from '@angular/common';

interface SelectModel {
  code : string , 
  name : string 
}

@Component({
  selector: 'app-accounts',
  imports: [ButtonModule , TableModule , ReactiveFormsModule , DialogModule , InputTextModule , InputNumberModule , SelectModule , TagModule , TabsModule , ToastModule ,FormsModule, DecimalPipe, UpperCasePipe],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
  providers :[MessageService]
})
export class Accounts implements OnInit{
  private messageService = inject(MessageService);
  private accountsService = inject(AccountServices);  
  private usersService = inject(UserServices)

  protected accounts!: Account[] 
  protected users?: User[] 
  
  protected addDialogVisible : boolean = false
  protected editDialogVisible : boolean = false

  protected selectedAccountType? : SelectModel 
  protected selectedAccountStatus? : SelectModel 
  protected accountToEdit? : Account 
  protected selectedUser? : SelectModel 

  loadingSaveAccount = false
  loadingGetAccounts = signal(true )
  loadingEditAccount = false; 

  protected accountTypes : SelectModel[] = 
            Object.values(AccountType)
            .filter(value => typeof value === "string")
            .map(element => { return { name : element.toString() , code : element.toString() } })
  
  protected accountStatus : SelectModel[] = 
            Object.values(AccountStatus)
            .filter(value => typeof value === "string")
            .map(element => { return { name : element.toString() , code : element.toString() } }) ; 

  protected usersToRender? : SelectModel[] 

  protected addAccountFormGroup = new FormGroup({
    "balance" : new FormControl(0 , [Validators.required] ) , 
    "accountType" : new FormControl<SelectModel | undefined>(undefined) , 
    "currency" :  new FormControl("MAD" , [Validators.required] ) ,  
    "status" : new FormControl<SelectModel | undefined>(undefined , [Validators.required]), 
    "user" : new FormControl<SelectModel | undefined>(undefined , [Validators.required] )
  })


  protected editAccountFormGroup = new FormGroup({
    "accountType" : new FormControl<SelectModel | undefined>(undefined) , 
    "status" : new FormControl<SelectModel | undefined>(undefined , [Validators.required]), 
  })

  getAddAccountFormControl(name : string) { 
    return this.addAccountFormGroup.get(name)
  }

  toEnumLabel(type: string, value: string): string { 
    if(type === "AccountType") return AccountType[value as keyof typeof AccountType]; 
    else return AccountStatus[value as keyof typeof AccountStatus];
  }

  showAddAccountDialog() {
    this.selectedAccountStatus = undefined
    this.selectedAccountType = undefined

    this.addDialogVisible = true 
  }

  showEditAccountDialog(account : Account) {
    this.accountToEdit = account

    this.selectedAccountType = { name : AccountType[account.accountType] , code :  AccountType[account.accountType]}
    this.selectedAccountStatus = { name : AccountStatus[account.status] , code :  AccountStatus[account.status]}

    this.editDialogVisible = true 
  }
  
  deleteAccount(id : string)  {
    this.accountsService.deleteAccount(id).subscribe({
      next :  (response) => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Deleted successfuly' });
        this.accounts = this.accounts?.filter(account => account.accountId != id) 
      } , 
      error:  (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot delete user! try again later' });
      }
    })
    
  }

  editAccount() {
    this.loadingEditAccount = true 
    if( this.editAccountFormGroup.get("status")?.valid 
        && this.editAccountFormGroup.get("accountType")?.valid 
    ){
      const accountRequest : Account = {
        accountId: this.accountToEdit?.accountId, 
        accountType : this.editAccountFormGroup.get("accountType")?.value?.code as AccountType , 
        balance : this.accountToEdit?.balance! , 
        currency : this.accountToEdit?.currency! , 
        status : this.editAccountFormGroup.get("status")?.value?.code as AccountStatus, 
        user : this.accountToEdit?.user!
      }
      this.accountsService.updateAccount(accountRequest).subscribe({
        next : (response) => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'updated successfuly' });
          this.loadAccounts()
        } , 
        error:  (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot update account! try again later' });
        }
      })

      this.loadingEditAccount = false
    }
  }

  saveAccount() {
    this.loadingSaveAccount = true 
    if( this.getAddAccountFormControl("balance")?.valid 
        && this.getAddAccountFormControl("user")?.valid 
        && this.getAddAccountFormControl("accountType")?.valid 
        && this.getAddAccountFormControl("status")?.valid 
        && this.getAddAccountFormControl("currency")?.valid 
    ){
      console.log("form valid")
      this.loadingSaveAccount = false 
      const accountRequest : Account = {
        accountType : this.getAddAccountFormControl("accountType")?.value.code , 
        balance : this.getAddAccountFormControl("balance")?.value , 
        currency : this.getAddAccountFormControl("currency")?.value , 
        status : this.getAddAccountFormControl("status")?.value.code , 
        user : {
          id : this.getAddAccountFormControl("user")?.value.code
        }
      }
      this.accountsService.createAccount(accountRequest).subscribe({
        next : (response) => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Account Created successfuly!' });
          this.accounts?.push(response) 
          this.loadingSaveAccount = false
        }, 
        error : (error) => {

          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot create account please try again later!' });
          this.loadingSaveAccount = false
        }

      })
      console.log(accountRequest)
    }else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'All fields required' });
    }
  }

  loadAccounts() {
    this.accountsService.getAllAccounts().subscribe({
      next :  (response) => {
        this.loadingGetAccounts.set(false) 
        this.accounts = response
        console.log(response)
      },
      error : (error) =>{

        this.loadingGetAccounts.set(false) 
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error during loading accounts'});
      }
    })
  }
  ngOnInit(): void {
    this.loadAccounts()
    this.usersService.getAllUsers().subscribe({
      next :  (response : any) => {
        this.usersToRender = response.map( (element : User) => ({name : `${element.firstName} ${element.lastName}` , code : element.id }))
      },
      error : (error) =>{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error during loading users'});
      }
    })

  }
}
