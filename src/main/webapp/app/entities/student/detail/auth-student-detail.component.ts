import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudent } from '../student.model';
import { StudentService } from '../service/student.service';

@Component({
  selector: 'jhi-student-detail',
  templateUrl: './student-detail.component.html',
})
export class AuthStudentDetailComponent implements OnInit {
  student: IStudent | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private studentService: StudentService) {}

  ngOnInit(): void {
    this.studentService.getAuthenticatedStudent().subscribe(student => {
      this.student = student.body;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
