/*DANIYAL PARVEEZ, 1BG15CS026, C.S.E 6-A, BNMIT*/
#include <stdio.h>
#include <conio.h>
#include <windows.h>
#include <gl/glut.h>
#include <sys/timeb.h>
#define HAVE_STRUCT_TIMESPEC

/*NOTE: GLUT library to project has not been added manually as a dependency.
Rather, freeglut has been installed locally for the project via NuGet Package Manager.
glut32 will also work, but the project has to be recompiled with 
glut32 as a dependency, and the edges for the boxes displayed will be jagged and darker.*/

/* Replaced operation of global tranlsate by -5 with several local translations of -5
to test something relating to mouse interactivity */

typedef struct process_struct {
	int pid;
	int bt; //burst_time
	int at; //arrival time

}process;

float avg_wt, avg_tat;

//belows structure contains data for animating processes thorugh ready queue.
typedef struct ready_queue_animation_struct {
	process proc;
	char label[3]; //label identifying proc.
	int iter; //no. of times proc. goes through ready queue
	float x, y, z; //for current x,y,z coordinates of 
	float xToReach; //x coord. destination in ready queue
	int alreadyVisible; //is process already on screen
}ready_queue_anim;

process *proc;
ready_queue_anim *rq, *temp_rq;

int useDefaultValues = 0; //use 1 for testing
int n_proc = 0, temp_n_proc;
int f, r; //front, rear variables for ready queue.
int quantum;

void drawCmdIntro();
void roundrobinSetup();
void roundrobin();
//--------------------------------------------------------------------------------------
float cube_v[8][3] = { { -1,-1,-1 },{ -1,1,-1 },{ 1,1,-1 },{ 1,-1,-1 },{ -1,-1,1 },{ -1,1,1 },{ 1,1,1 },{ 1,-1,1 } };
float zTransform = -27.0f; //How much should all objects be translated along -Z axis.

float hlsX = -7, hlsY = 0; //starting coords. for hls line
float animationSpeed = 2.5f;

//how much translation have keyboard keys 
float leftTranslated = 0, rightTranslated = 0;
float forwardTranslated = 0, backwardTranslated = 0;

int animationStarted = 0;
int animationPaused = 1; //is animation paused
int isCpuOccupied = 0; //is there a process in the CPU- 0 (no), 1 (about to enter), 2 (entered)
int readyQueueSize; //no. of boxes to be shown for queue
int finishedIntro = 0; //should intro screen be displayed
int minPidForStatus = 0; //Display process statuses with pid from minPidStatus to maxPidStatus+2
int lastProcessFinished; //what was the last process to pass through CPU
int isProcessDone; //0 - finished execution, 1 - still remaining
int isGlobalTranslateDoneOnce = 0; //do a global translate if necessary to make sure things are centered

char processStatusMsg[50] = " "; //some message to be displayed
char *procSequence; //contains sequence for processes.

void init_mygl();

void menu(int choice);
void reshape(GLsizei, GLsizei);
void keyHandler(unsigned char key, int x, int y); //handle key events
void specialKeyHandler(int key, int x, int y); //handle arrow key events
void mouseMotionHandler(int bt, int state, int x, int y);

void draw_intro(); //display intro screen

void draw_square(float *, float *, float *, float *);
void draw_box(); //draw single box
void draw_lines();
void draw_boxes(); //for drawing all the boxes

void draw_3d_text(char *, float x, float y, float z, float sx, float sy, float sz);
void draw_2d_text(char *str, float x, float y, float z, int font);

void draw_screen2_texts(); //handles all strings to be pushed to screen.
void draw_proc_labels(); //draw process labels


void animationSetup();
void animate(); //CRAAAAAAAAAAAAAAZZZZZZZZZZZZZZZZYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
void display();


int main(int argc, char **argv)
{
	drawCmdIntro();
	roundrobinSetup(); //perform necessary setup before roundrobin alg. starts.
	getch();
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH | GLUT_MULTISAMPLE);
	glutInitWindowSize(800, 600);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("Preemptive Scheduling with Round Robin");
	glutFullScreen();

	init_mygl();
	glEnable(GL_DEPTH_TEST);
	glutDisplayFunc(display);
	glutReshapeFunc(reshape);
	glutKeyboardFunc(keyHandler);
	glutSpecialFunc(specialKeyHandler);
	glutMouseFunc(mouseMotionHandler);
	glutIdleFunc(animate);

	glutMainLoop();

	getch();
	return 0;
}

void drawCmdIntro()
{
	printf("ROUND-ROBIN SCHEDULING WITH OPENGL\n");
	printf("\nA project by - DANIYAL PARVEEZ, 1BG15CS026");
	printf("\nStudent of 6th Semester, C.S.E - A");
	printf("\nBNM Institute of Technology, Bangalore");
	printf("\n-------------------------------------------\n\n");
	getch();
	
}
void roundrobinSetup()
{
	int i, j;
	ready_queue_anim swap;
	printf("Enter no. of processes: ");
	if (useDefaultValues == 1)
		n_proc = 4;
	else
		scanf("%d", &n_proc);

	temp_n_proc = n_proc;

	proc = (process*)malloc(n_proc * sizeof(process));
	rq = (ready_queue_anim*)malloc(n_proc * sizeof(ready_queue_anim));
	temp_rq = (ready_queue_anim*)malloc(n_proc * sizeof(ready_queue_anim));

	for (i = 0; i < n_proc; i++)
	{
		proc[i].pid = i;
		printf("\nEnter Burst Time for Process [%d]: ", proc[i].pid);
		if (useDefaultValues == 1)
		{
			proc[i].bt = (i + 1) * 5;
		}
		else
		{
			scanf("%d", &proc[i].bt);
		}
		proc[i].at = 0;

		rq[i].proc = proc[i];
		rq[i].iter = 0;
		rq[i].x = rq[i].y = rq[i].z = 0;

	}
	printf("\n\nEnter time quantum (in msec): ");
	if (useDefaultValues == 1)
		quantum = 5;
	else
	{
		scanf("%d", &quantum);
	}

	/*sort structures on basis of arrival time
	for (i = 0; i < n_proc - 1; i++)
	{
		for (j = 0; j < (n_proc - 1 - i); j++)
		{
			if (rq[j].proc.at > rq[j + 1].proc.at)
			{
				swap = rq[j];
				rq[j] = rq[j + 1];
				rq[j + 1] = swap;
			}
		}
	}
	*/
	for (i = 0; i < n_proc; i++)
	{
		temp_rq[i].proc = rq[i].proc;
		temp_rq[i].iter = 0;
		temp_rq[i].x = rq[i].y = rq[i].z = 0;
	}
	roundrobin();
}

void roundrobin()
{
	int *temp, *wt, tat = 0; //burst, wait and turnaoround times
	temp = (int*)malloc(n_proc * sizeof(int));
	wt = (int*)malloc(n_proc * sizeof(int));

	int sum_wt = 0, sum_tat = 0;

	int n = n_proc;
	int i = 0;
	int flag = 0; //set to 1 when a process has completed


				  //initialize malloc arrays
	for (; i < n_proc; i++)
	{
		temp[i] = rq[i].proc.bt;
		wt[i] = 0;
	}
	printf("\nProcess ID\tBurst Time\tTurnaround Time\t Waiting Time\n");

	i = 0;

	while (n)
	{
		if (temp[i] <= quantum && temp[i] > 0)
		{
			flag = 1;
			tat += temp[i];

			temp[i] = 0;

			rq[i].iter++;
			temp_rq[i].iter++;


		}
		else if (temp[i] > 0)
		{
			tat += quantum;

			temp[i] -= quantum;
			rq[i].iter++;
			temp_rq[i].iter++;

		}
		if (temp[i] == 0 && flag == 1)
		{
			n--;

			printf("\nProcess[%d]\t\t%d\t\t%d\t\t%d", proc[i].pid, proc[i].bt, tat, tat - proc[i].bt);
			sum_wt += tat  - proc[i].bt;
			sum_tat += tat ;
			flag = 0;
		}
		i++;
		if (i > n_proc - 1)
			i = 0;
	}
	avg_wt = sum_wt * 1.0 / n_proc;
	avg_tat = sum_tat * 1.0 / n_proc;
	printf("\n\nAverage Waiting Time:\t%f", avg_wt);
	printf("\nAvg Turnaround Time:\t%f\n", avg_tat);
	printf("\n");

}
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


void reshape(GLsizei width, GLsizei height) {

	if (height == 0) height = 1;  // To prevent divide by 0
	GLfloat aspect = (GLfloat)width / (GLfloat)height;

	// Set the viewport to cover the new window
	glViewport(0, 0, width, height);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(45, aspect, 10, 1000);
}

void menu(int choice)
{
	switch (choice)
	{
	case 1: exit(0); break; //exit app

	case 2: if (animationStarted == 0) {  //Play or Pause animation
		animationStarted = 1;
		animationSetup();
	}
			animationPaused = !animationPaused;
			break;
	case 3: animationSpeed = 2.5f; break;

	}
}
void keyHandler(unsigned char key, int x, int y)
{
	int i = 0;

	if (finishedIntro == 0)
	{
		finishedIntro = 1;
		glutAddMenuEntry("Play/Pause Animation", 2);
		glutAddMenuEntry("Reset Animation Speed", 3);
		return;
	}
	switch (key)
	{
	case 'W':
	case 'w': glTranslated(0, 0, -0.2);
		forwardTranslated -= 0.2;
		break;
	case 'S':
	case 's': glTranslated(0, 0, 0.2);
		backwardTranslated += 0.2;
		break;
	case 'E':
	case 'e': if (minPidForStatus + 3 < n_proc)
		minPidForStatus += 3;
			  else
				  minPidForStatus = 0;
		break;
	case '+':if (animationSpeed < 15.5)
		animationSpeed += 0.25; break;

	case '-': if (animationSpeed > 0.5)
		animationSpeed -= 0.25; break;

	}
	glutPostRedisplay();
}

void specialKeyHandler(int key, int x, int y)
{
	switch (key)
	{
	case GLUT_KEY_LEFT: glTranslated(-0.1, 0, 0);
		leftTranslated -= 0.1;

		break;
	case GLUT_KEY_RIGHT: glTranslated(0.1, 0, 0);
		rightTranslated += 0.1;
		break;


	}
	glutPostRedisplay();
}

void mouseMotionHandler(int bt, int state, int x, int y)
{
	int i;
	GLint viewport[4];
	GLdouble modelview[16];
	GLdouble projection[16];
	GLfloat winX, winY, winZ;
	GLdouble posX, posY, posZ;
	glGetDoublev(GL_MODELVIEW_MATRIX, modelview);
	glGetDoublev(GL_PROJECTION_MATRIX, projection);
	glGetIntegerv(GL_VIEWPORT, viewport);
	winX = (float)x;
	winY = (float)viewport[3] - (float)y;

	gluUnProject(winX, winY, 0, modelview, projection, viewport, &posX, &posY, &posZ);

	posX = posX - 4;

}

void init_mygl()
{
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, (GLsizei)800, (GLsizei)600);
	//glOrtho(-3, 5.5 + 2 * n_proc, -6, 2.5 + 2*n_proc, -10, 10);
	glClearColor(1, 1, 1, 1);
	glutCreateMenu(menu);
	glutAddMenuEntry("Exit Application", 1);
	glutAttachMenu(GLUT_RIGHT_BUTTON);
	//for anit-aliasing
	glEnable(GL_LINE_SMOOTH);
	glEnable(GL_POLYGON_SMOOTH);
	glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
	glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
}

void draw_square(float *a, float *b, float *c, float *d)
{
	glBegin(GL_QUADS);
	glVertex3fv(a);
	glVertex3fv(b);
	glVertex3fv(c);
	glVertex3fv(d);
	glEnd();
	glFlush();
}

void draw_box()
{
	draw_square(cube_v[0], cube_v[3], cube_v[2], cube_v[1]);
	draw_square(cube_v[4], cube_v[7], cube_v[6], cube_v[5]);
	draw_square(cube_v[7], cube_v[3], cube_v[2], cube_v[6]);
	draw_square(cube_v[4], cube_v[0], cube_v[1], cube_v[5]);
	draw_square(cube_v[5], cube_v[6], cube_v[2], cube_v[1]);
	draw_square(cube_v[4], cube_v[7], cube_v[3], cube_v[0]);
}

void draw_lines()
{

	int i;
	float angle;

	glColor3f(0, 0, 0);
	glPushMatrix();
	glTranslated(-5, 0, 0);
	glBegin(GL_LINES);

	//Arrow for CPU Scheduler
	glVertex3f(-7, 0, zTransform);
	glVertex3f(-1, 0, zTransform);

	glVertex3f(-1, 0, zTransform);
	glVertex3f(-1.5, 0.5, zTransform);

	glVertex3f(-1, 0, zTransform);
	glVertex3f(-1.5, -0.5, zTransform);

	//Arrow for LLS
	glVertex3f(-1 + 2 * readyQueueSize, 0, zTransform);
	glVertex3f(-1 + 2 * readyQueueSize + 3, 0, zTransform);

	glVertex3f(-1 + 2 * readyQueueSize + 3, 0, zTransform);
	glVertex3f(-1 + 2 * readyQueueSize + 3 - 0.5, 0.5, zTransform);

	glVertex3f(-1 + 2 * readyQueueSize + 3, 0, zTransform);
	glVertex3f(-1 + 2 * readyQueueSize + 3 - 0.5, -0.5, zTransform);
	glEnd();
	glPopMatrix();

	glPushMatrix();
	glBegin(GL_LINES);
	//CPU pins

	for (i = 0; i < 11; i++)
	{
		glVertex3f(readyQueueSize * 2 - 3 + i * 0.37, 1.75, zTransform);
		glVertex3f(readyQueueSize * 2 - 3 + i * 0.37, 2.55, zTransform);
	}

	for (i = 0; i < 11; i++)
	{
		glVertex3f(readyQueueSize * 2 - 3 + i * 0.37, -1.75 + i * 0.015, zTransform);
		glVertex3f(readyQueueSize * 2 - 3 + i * 0.37, -2.55, zTransform);
	}

	//arrow for exit
	if (isCpuOccupied == 3 && temp_rq[0].iter == 1)
	{
		glColor3f(0.600, 0.196, 0.800); //dark orchid
		glVertex3f(readyQueueSize * 2 + 2, -0.25, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5, -0.25, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5, -0.25, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5 - 0.45, -0.25 + 0.3, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5, -0.25, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5 - 0.45, -0.25 - 0.3, zTransform);
	}

	//arrow for re-enter
	else if (isCpuOccupied == 3 && temp_rq[0].iter > 1)
	{
		glColor3f(0.600, 0.196, 0.800); //dark orchid
		glVertex3f(readyQueueSize * 2 + 2, -0.25, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5, -0.25, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5, -0.25, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5, -4.5, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5, -4.5, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5 - 15, -4.5, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5 - 15, -4.5, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5 - 15 + 0.5, -4.5 + 0.5, zTransform);

		glVertex3f(readyQueueSize * 2 + 6.5 - 15, -4.5, zTransform);
		glVertex3f(readyQueueSize * 2 + 6.5 - 15 + 0.5, -4.5 - 0.5, zTransform);
	}
	glEnd();
	glPopMatrix();



}

void draw_boxes()
{
	int i = 0;
	//to draw a minimum of 6 boxes for ready queue
	readyQueueSize = n_proc >= 6 ? n_proc : 6;

	if (!isGlobalTranslateDoneOnce && readyQueueSize > 6)
	{
		glTranslatef((6 - readyQueueSize) * 1.25, 0, (6 - readyQueueSize) * 1);
		leftTranslated += (6 - readyQueueSize) * 1.25;
		forwardTranslated += (6 - readyQueueSize) * 1;
		isGlobalTranslateDoneOnce = 1;
	}
	glColor3f(0, 0, 0);
	for (i = 0; i < readyQueueSize; i++)
	{
		glPushMatrix();

		glTranslated(i * 2 - 5, 0, zTransform); //translate by 2 units.
		glRotatef(10, 1, 1, 0);
		draw_box();
		glPopMatrix();
	}

	//draw block for cpu
	glPushMatrix();
	glColor3f(1, 0.5, 0);

	glTranslated(i * 2 - 1, 0, zTransform);
	glRotatef(10, 1, 1, 0);
	glScalef(2.25, 1.75, 1);

	draw_box();
	glPopMatrix();

}

void draw_3d_text(char *str, float x, float y, float z, float sx, float sy, float sz)
{
	int i;

	glPushMatrix();
	glTranslatef(x, y, z);
	glScalef(sx, sy, sz);

	for (i = 0; str[i] != '\0'; i++)
		glutStrokeCharacter(GLUT_STROKE_ROMAN, str[i]);

	glPopMatrix();
}

void draw_2d_text(char *str, float x, float y, float z, int font)
{
	int i;
	glRasterPos3f(x, y, z);
	for (i = 0; str[i] != '\0'; i++)
		glutBitmapCharacter(font, str[i]);
}


void draw_intro()
{
	glPushMatrix();
	glTranslated(-5, 0, 0);
	glColor3f(0, 0, 0);
	draw_3d_text("B.N.M. Institute of Technology", -40, 50, -150, 0.045, 0.045, 0.045);
	draw_3d_text("Round-Robin Scheduling Simulation", -65, 5, -150, 0.06, 0.06, 0.06);
	draw_3d_text("DANIYAL PARVEEZ", 75, -40, -150, 0.02, 0.02, 0.02);
	draw_3d_text("1BG15CS026", 75, -45, -150, 0.02, 0.02, 0.02);
	draw_3d_text("VI-SEM / C.S.E-A", 75, -50, -150, 0.02, 0.02, 0.02);
	draw_3d_text("Press any key to advance to next screen...", -40, -55, -150, 0.025, 0.025, 0.025);
	glPopMatrix();
}

void draw_screen2_texts()
{
	int i, j, k;
	char str[55];
	glPushMatrix();
	glTranslated(-5, 0, 0);
	glColor3f(0.22, 0.68, 0.87);

	//"Ready Queue"
	strcpy(str, "Ready Queue");
	draw_3d_text(str, (cube_v[0][0] + readyQueueSize * 2) / 2 - strlen(str) / 2, cube_v[1][1] + 0.5, zTransform, 0.008, 0.008, 0.008);

	//"CPU"
	strcpy(str, "CPU");
	if (isCpuOccupied != 2)
		draw_3d_text(str, (cube_v[0][0] + readyQueueSize * 2) + 5.5 - strlen(str) / 2.0f, cube_v[0][1] + 1, zTransform, 0.008, 0.008, 0.008);

	glColor3f(0.863, 0.078, 0.235);
	//"CPU Scheduler"
	draw_3d_text("CPU Scheduler", (-7 - (-1)) / 2 - 3.5, 0.2, zTransform, 0.004, 0.004, 0.004);
	//"LLS"
	draw_3d_text("LLS", -1 + 2 * readyQueueSize + 1.2, 0.2, zTransform, 0.004, 0.004, 0.004);

	glColor3f(1, 0.5, 0.2);

	//text for key map	
	draw_2d_text("KEYMAP", -8 - leftTranslated - rightTranslated, -4.5, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
	draw_2d_text("_______________", -8 - leftTranslated - rightTranslated, -4.55, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
	draw_2d_text("W - Zoom out", -8 - leftTranslated - rightTranslated, -5.2, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
	draw_2d_text("S - Zoom In", -8 - leftTranslated - rightTranslated, -5.6, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
	draw_2d_text("E - Cycle through Process Info", -8 - leftTranslated - rightTranslated, -6.0, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	if (animationSpeed < 15.5)
		draw_2d_text("(+) - Increase Animation Speed", -8 - leftTranslated - rightTranslated, -6.8, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
	if (animationSpeed > 0.5)
		draw_2d_text("(-) - Reduce Animation Speed", -8 - leftTranslated - rightTranslated, -7.2, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	draw_2d_text(processStatusMsg, 2.5 - leftTranslated - rightTranslated, -7.5, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	//process info on top
	glPushMatrix();
	for (i = minPidForStatus; i < minPidForStatus + 3; i++)
	{
		if (i != minPidForStatus)
			glTranslated(10, 0, 0);

		if (i < n_proc)
		{
			for (j = 0; rq[j].proc.pid != i; j++); //so that processes are printed in order.
			for (k = 0; temp_rq[k].proc.pid != i; k++); // to print current 'iter' value.

			if (temp_rq[k].iter > 0)
				glColor3f(0, 1, 0);
			else
				glColor3f(1, 0, 0);

			sprintf(str, "Process P%d info:-", i);
			draw_2d_text(str, -8 - leftTranslated - rightTranslated, 7.8, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
			draw_2d_text("____________________", -8 - leftTranslated - rightTranslated, 7.75, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

			sprintf(str, "Burst Time: %d ms", rq[j].proc.bt);
			draw_2d_text(str, -8 - leftTranslated - rightTranslated, 7.3, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

			sprintf(str, "Arrival Time: %d ms", rq[j].proc.at);
			draw_2d_text(str, -8 - leftTranslated - rightTranslated, 6.9, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

			sprintf(str, "Total quanta assigned: %d ", rq[j].iter);
			draw_2d_text(str, -8 - leftTranslated - rightTranslated, 6.5, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

			sprintf(str, "Quanta Remaining: %d ", temp_rq[k].iter);
			draw_2d_text(str, -8 - leftTranslated - rightTranslated, 6.1, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);
		}

	}
	glPopMatrix();

	if (isCpuOccupied == 3 && temp_rq[0].iter == 1)
	{
		glColor3f(0.698, 0.133, 0.133); //firebrick
		draw_3d_text("EXIT", readyQueueSize * 2 + 8.6, -0.72, zTransform, 0.004, 0.004, 0.004);
	}
	else if (isCpuOccupied == 3)
	{
		glColor3f(0.000, 1.000, 0.000); //lime
		draw_3d_text("RE-ENTER", readyQueueSize * 2 + 8.3, -0.72, zTransform, 0.004, 0.004, 0.004);
	}

	glColor3f(1.000, 0.843, 0.000); //gold
	sprintf(str, "Total no. of processes: %d", n_proc);
	draw_2d_text(str, 12 - leftTranslated - rightTranslated, -5, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	sprintf(str, "Average Waiting Time: %0.2f ms", avg_wt);
	draw_2d_text(str, 12 - leftTranslated - rightTranslated, -5.5, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	sprintf(str, "Average Turnaround Time: %0.2f ms", avg_tat);
	draw_2d_text(str, 12 - leftTranslated - rightTranslated, -6, -20 - forwardTranslated - backwardTranslated, GLUT_BITMAP_HELVETICA_18);

	glPopMatrix();
}



void animationSetup()
{
	int i, j;
	char str[4];

	float prev_x = hlsX - 5; //x, postion of the previous process

	glColor3f(1.000, 0.871, 0.67);


	for (i = 0; i < n_proc; i++)
	{
		rq[i].x = prev_x - 2 - 1 * rq[i].proc.at;
		rq[i].y = hlsY + 0.2;
		rq[i].z = zTransform;

		rq[i].xToReach = -1 + readyQueueSize * 2 - 1 - 2 * i - 5; //-5 to ACCOUNT FOR THE iniitial translation

		sprintf(str, "P%d", rq[i].proc.pid);
		strcpy(rq[i].label, str);

		prev_x = rq[i].x;

		temp_rq[i].x = rq[i].x;
		temp_rq[i].y = rq[i].y;
		temp_rq[i].z = rq[i].z;

		temp_rq[i].xToReach = rq[i].xToReach;
		strcpy(temp_rq[i].label, rq[i].label);

		temp_rq[i].proc = rq[i].proc;
		temp_rq[i].iter = rq[i].iter;
	}
	temp_n_proc = n_proc;
}

void animate()
{

	int i = 0;
	float tempXToReach;
	char str[3];


	if (!animationStarted || animationPaused)
		return;
	glColor3f(1, 1, 1);

	tempXToReach = temp_rq[temp_n_proc - 1].xToReach - 2;


	for (i = 0; i < temp_n_proc; i++)
	{
		if (temp_rq[i].x <= temp_rq[i].xToReach)
		{

			if (isCpuOccupied == 2 && i == 0)
				temp_rq[0].x += 0.035 / temp_rq[i].proc.bt * animationSpeed;
			else if (isCpuOccupied == 1)
				temp_rq[i].x += 0.01 * animationSpeed;
			else
				temp_rq[i].x += 0.02 * animationSpeed;
		}

	}

	if (isCpuOccupied == 0 && temp_rq[0].x >= temp_rq[0].xToReach)
	{
		//temp_rq[0].z += 5;				
		for (i = 1; i < temp_n_proc; i++)
			temp_rq[i].xToReach += 2;
		temp_rq[0].xToReach += 4;

		isCpuOccupied = 1;		//proc. about to enter cpu				
	}

	else if (isCpuOccupied == 1 && temp_rq[0].x >= temp_rq[0].xToReach)
	{

		isCpuOccupied = 2; //proc. entered cpu
		temp_rq[0].xToReach += 4.2;


	}

	else if (isCpuOccupied == 2 && temp_rq[0].x >= temp_rq[0].xToReach)
	{
		isCpuOccupied = 3; //proc. leaving cpu		
		temp_rq[0].xToReach += 5.5;

		lastProcessFinished = temp_rq[0].proc.pid;
		if (temp_rq[0].iter - 1 == 0) //if process finishing execution
			isProcessDone = 1;
		else isProcessDone = 0;
		if (isProcessDone)
			sprintf(processStatusMsg, "Process P%d finished execution", lastProcessFinished);
		else
			sprintf(processStatusMsg, "Process P%d has %d more quanta.", lastProcessFinished, temp_rq[0].iter - 1);


	}
	else if (isCpuOccupied == 3 && temp_rq[0].x >= temp_rq[0].xToReach)
	{
		ready_queue_anim temp;

		--temp_rq[0].iter;

		//to rearrange processes
		temp = temp_rq[0];

		if (temp_n_proc > 1)
		{
			for (i = 0; i < temp_n_proc - 1; i++)
				temp_rq[i] = temp_rq[i + 1];
			temp_rq[i] = temp;
		}

		if (temp_rq[temp_n_proc - 1].iter == 0)
		{
			temp_n_proc--;

		}
		else
		{
			temp_rq[temp_n_proc - 1].x = hlsX - 7;
			temp_rq[temp_n_proc - 1].xToReach = -1 + readyQueueSize * 2 - 1 - (temp_n_proc - 1) * 2 - 5; //reach one block before last process in queue
		}

		if (temp_n_proc == 0)
		{
			animationPaused = 1;
			animationStarted = 0;
			strcpy(processStatusMsg, " ");

		}


		isCpuOccupied = 0;
	}


	glutPostRedisplay();
}

void draw_proc_labels()
{
	int i = 0;
	glLineWidth(2);
	glColor3f(0, 0.2, 1);
	for (i = 0; i < temp_n_proc; i++)
	{
		if (temp_rq[i].iter != 0)
			draw_3d_text(temp_rq[i].label, temp_rq[i].x, temp_rq[i].y, temp_rq[i].z, 0.004, 0.004, 0.004);
	}
	glLineWidth(1);
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	if (finishedIntro == 0)
	{
		draw_intro();
	}
	else
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		draw_boxes();
		draw_lines();
		draw_screen2_texts();
		if (animationStarted)
			draw_proc_labels();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	glutSwapBuffers();

}
