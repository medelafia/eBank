import { Component, inject, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { MoveUpRight , LucideAngularModule} from 'lucide-angular';
import { ActivatedRoute, Router } from '@angular/router';
import { AvatarModule } from 'primeng/avatar';
import { PopoverModule } from 'primeng/popover';
import { UserServices } from '@/services/user-services/user-services';
import { User } from '@/models/user';
import { AccountServices } from '@/services/account-services/account-services';
import { Account } from '@/models/account';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';

@Component({
  selector: 'app-client',
  imports: [ButtonModule , TableModule , LucideAngularModule , AvatarModule , PopoverModule , DialogModule , InputTextModule , InputNumberModule],
  templateUrl: './client.html',
  styleUrl: './client.css',
})
export class Client implements OnInit{
  MoveUpRight = MoveUpRight
  recentTransactions : {id : string , date : string , time : string , to : string , amount : string }[] = []
  private router : Router = inject(Router) 
  private activatedRoute : ActivatedRoute = inject(ActivatedRoute)
  private userService : UserServices = inject(UserServices)
  private accountServices : AccountServices = inject(AccountServices)
  protected userAccounts? : Account[]  
  protected activeAccount? : Account

  protected userId : string = this.activatedRoute.snapshot.params['id']
  protected user? : User 

  protected depositDialogVisible : boolean = false 


  showDepositDialog(){
    this.depositDialogVisible = true
  }

  deposit() { 
  }

  logout() {
    this.router.navigate(['/login'])
  } 
  
  showWithdrawDialog(){
    
  }
  
  showTransferDialog(){

  }

  ngOnInit(): void {
    this.userService.getUserById(this.userId).subscribe({
      next : (response) => {
        console.log(response)
        this.user = response

      } , 
      error : (error) => {
        
      }
    })
    this.accountServices.getAllAccountsyUserId(this.userId).subscribe({
      next : (response) => { 
        console.log(response)
        this.userAccounts = response
        this.activeAccount = this.userAccounts[0]
        console.log(this.activeAccount)
      }, 
      error : (error) => {
        console.log(error)
      }
    })
  }
  switchAccount(accountId : string){
    this.activeAccount = this.userAccounts?.filter(account => account.accountId == accountId)[0]
  }
}

