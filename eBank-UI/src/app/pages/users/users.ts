import { User } from '@/models/user';
import { UserServices } from '@/services/user-services/user-services';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { DrawerModule } from 'primeng/drawer';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';


@Component({
  selector: 'app-users',
  imports: [TableModule  , ButtonModule , MessageModule , DialogModule , InputTextModule , FormsModule , ReactiveFormsModule , DrawerModule, ToastModule],
  templateUrl: './users.html',
  styleUrl: './users.css' , 
  providers : [MessageService]
})
export class Users implements OnInit{
  private messageService : MessageService = inject(MessageService)
  private readonly userService : UserServices = inject(UserServices) 
  protected users! : User[] 
  protected loading = signal(true)

  protected dialogVisible : boolean = false
  protected firstName : string = ""

  addDialogVisible : boolean = false
  editDialogVisible : boolean = false
  
  userToEdit? : User 

  editLoading? : boolean = false
  saveLoading? : boolean = false

  userFormGroup : FormGroup = new FormGroup({
    "firstName" : new FormControl("", [Validators.required , Validators.minLength(5)]), 
    "lastName" : new FormControl("", [Validators.required , Validators.minLength(5)]), 
    "email" : new FormControl("", [Validators.required , Validators.email]), 
    "phoneNumber" : new FormControl("", [Validators.required]), 
  })

  deleteUserById(userId : string) : void { 
    console.log(userId)
    this.userService.deteleUserById(userId).subscribe({
      next : () => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User deleted successfuly!' });
        this.users = this.users.filter(element => element.id != userId)
      } , 
      error : () => { 
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error during deleting user!' });
      }
    })
  }  
  
  showAddUserDialog() {
    this.addDialogVisible = true 
  }

  showEditUserDialog(user : User) {
    this.editDialogVisible = true 
    this.userToEdit = user
  }


  editUser() {
    console.log(this.userToEdit)
    this.editLoading = true
    if(this.userToEdit!=undefined) {
      this.userService.editUser(this.userToEdit!).subscribe({
        next : (response) => {
          this.editLoading = false
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User updated successfuly!' });
          console.log(response)
        }, 
        error : (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot update user' });
          this.editLoading = false
        }
      })
    }
  }

  getUserFormControl(name : string) {
    return this.userFormGroup.get(name)
  }
  saveUser() { 
    this.saveLoading = true 

    if(
      this.getUserFormControl("firstName")?.valid &&
      this.getUserFormControl("lastName")?.valid &&
      this.getUserFormControl("email")?.valid &&
      this.getUserFormControl("phoneNumber")?.valid
    ){
      const userRequest : User = {
        firstName : this.getUserFormControl("firstName")?.value ,
        lastName : this.getUserFormControl("lastName")?.value ,
        email : this.getUserFormControl("email")?.value ,
        phoneNumber : this.getUserFormControl("phoneNumber")?.value
      }
      this.userService.saveUser(userRequest).subscribe({
        next : (response) => {
          console.log(response) 
          this.saveLoading = false
          this.users.push(response)
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User saved successfuly!' });
        } , 
        error : (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot save user' });
        }
      })
    }else {
      this.saveLoading = false 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'All fields required! Check form fields' });
    }
  }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe({
      next : (response) => {
        console.log(response)
        this.loading.set(false)
        this.users = response
      } , 
      error : (error) => {
        this.loading.set(false)
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Cannot load users' });
      }
    })
  }

}
