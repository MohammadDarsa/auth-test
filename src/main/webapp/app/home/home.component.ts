import { Component, OnInit } from '@angular/core';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { StudentService } from '../entities/student/service/student.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  account: Account | null = null;

  constructor(private accountService: AccountService, private loginService: LoginService, private studentService: StudentService) {}

  ngOnInit(): void {
    this.accountService.identity(true).subscribe(account => {
      this.account = account;
      if (account == null) this.loginService.logout();
    });
    this.studentService.getAuthenticatedStudent().subscribe(
      student => {
        console.log('complete');
      },
      err => {
        console.log('error');
        this.loginService.logout();
      }
    );
    // if (this.account == null) this.loginService.logout();
  }

  login(): void {
    this.loginService.login();
  }
}
