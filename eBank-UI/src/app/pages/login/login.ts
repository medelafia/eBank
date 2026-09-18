import { Component, inject } from '@angular/core';
import { FormControl, FormGroup , ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { LucideAngularModule , UserRoundKey} from 'lucide-angular';


@Component({
  selector: 'app-login',
  standalone : true , 
  imports: [ReactiveFormsModule , InputTextModule , ButtonModule , CheckboxModule , ToastModule , LucideAngularModule , ],
  templateUrl: './login.html',
  styleUrl: './login.css', 
  providers: [MessageService]
})
export class Login {
  UserRoundKey = UserRoundKey
  private messageService = inject(MessageService);
  private router : Router = inject(Router) 
  loginForm : FormGroup = new FormGroup({
    username : new FormControl("" , [Validators.required , Validators.minLength(8)]) , 
    password : new FormControl("" , [Validators.required , Validators.minLength(8)]) , 
    rememberMe : new FormControl(false) 
  })
  loading : boolean = false

  
  getFormControl( name : string ) { 
    return this.loginForm.get(name)
  }
  
  login(){
    if(this.getFormControl("username")?.valid && this.getFormControl("password")?.valid) { 
      this.loading = true 
      if(this.getFormControl("username")?.value == "mohamedelafia" && this.getFormControl("password")?.value == "mohamedelafia" ) {
        this.loading = false
        this.router.navigate(["/"])
      }else { 
        this.loading = false
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Invalid username or password' }); 
      }
    }else{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'All Fields required' }); 
    }
  }
}
