import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms'
import { SocialPostService } from './services/social-post.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  socialForm!:FormGroup;
  constructor(private socialPostService: SocialPostService,
    private fb: FormBuilder) { }


    socialForms(){
      this.socialForm = this.fb.group({
        link:['',Validators.required],
        message:['',Validators.required],
        imageAddress:['',Validators.required],
        platforms: this.fb.group({
          facebok:[''],
          twitter:[''],
          pinterest:['']       
         })
      })
    }
    
    get link() {return this.socialForm.controls} 
    get message(){return this.socialForm.controls}
    get imageAddress(){return this,this.socialForm.controls}
    get platforms(){};

  ngOnInit(): void {
    this.socialForms();
  // this.socialForm = new FormGroup({
  //   link: new FormControl(null, Validators.required),
  //   message: new FormControl(null),
  //   imageAddress: new FormControl(null),
  //   platforms: new FormArray([
  //     new FormControl(null),
  //     new FormControl(null),
  //     new FormControl(null)
  //   ])
  // });

   }

   postData(){
     console.log(this.socialForm.value);
   }

  post(link: string, message: string, platforms: any, imageAddress: string) {
  
    this.socialPostService.postToSocial(link, message, platforms, imageAddress).subscribe((result) => {
      console.log(result);
    });
  }
}
