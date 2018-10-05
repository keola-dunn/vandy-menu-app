# Vandy Menu App

 This year, Vanderbilt has implemented a number of campus dining changes that
have significantly affected students and their dining habits. The addition of new 
halls, removal of old staples, and a general refreshing of the existing options has
meant that menus across campus have been significantly changed from years prior.
As someone who lives on Highland Quad, these changes are even more significant,
as the costs in travel time and effort to get to various locations is higher, and thus “trying” a 
new place or option for a meal is much more risky. This same dilemma is impacts
students on Blakemore Quad. This project aims to help students
learn about and explore the available dining and menu options more easily and communicate
Vanderbilt dining options such that they can all be fully experienced. The intended way to
go about this is through a text messaging application that will communicate campus dining options
to students on a day to day basis.

Through the interviews, it is apparent that some students already access the 
campus dining website and application to view menu options. Some students indicated that
receiving dining info on their phones is or would be the best way to receive dining
information. These feedback invite the use of a text messaging application, where students
could text inquiries about what is available to eat each day. This sort of usage would
help students decide where to walk to get meals, and eliminate the potential to walk 
to a location without an option they are interested in. Additionally, this could help
students find foods on campus they might not have known was an option before, for example,
barbecue on Commons over the weekends. Additionally, this sort of application could help
students explore other existing options on Vanderbilt’s campus by indicating to them what
is available. An added side effect of this is that it could help to reduce lines during the
most hectic meal times on campus. 

The premise for this solution is simple. In order to better provide students with
current menu options which will help streamline decision processes and improve student meal
exposure, this application will allow students to text an inquiry to a specific phone number
and receive menu options for the day and time based on their inquiry. This will in theory be
faster than the mobile application and more convenient than the web page. This application
will specifically benefit students on Blakemore and Highland, as they are the two groups of
students with the largest costs (in terms of travel time) into each meal. This will enable
them specifically to make better decisions about where to go for food, which will shorten 
travel time and the decision processes. Functionally, the first vision for the project is 
that the application will focus on the dining locations with variable menus, primarily EBI,
Kissam, and Chef James. The menu returned will also be time sensitive, so an inquiry during
the lunch period will return the lunch menu and dinner hours a dinner menu. Additionally, the
app will indicate if the inquired location is open or not. The perfect end product will do this
using a combination of static data and a web scraper, however time restrictions may limit 
functionality to potentially entirely static data or a response containing a hyperlink. 

# Questions:
  1. How much do menu options influence where you eat on campus?
  2. How often do you use the campus dining website or application?
  3. Why do/don’t you use the campus dining website or application?
  4. Is there anywhere on campus that you do not eat? Why or why not?
  5. Why do you eat at the places you normally do?
  6. Are there foods that you think campus should offer and doesn’t
     (to your knowledge)?
  7. What would be the best way to get you menu information?
  8. Do you prefer menus that change (Kissam) or static menus (the Pub)?
  9. Have you ever not eaten on campus because you could not find an
     appealing option?
 10. How often do you walk to commons to get food?

# Answers:

## Question 1:
   Menu options always influence where I eat on campus. I never go to eat 
somewhere without an idea of what I’m eating.
   
I like soup, so I go where there is good soup on campus.
   
Menu options always determine where I eat. 

## Question 2:
   I rarely use the campus dining application or website.
   
I use the app probably once a week. 
   
I use the website daily, often multiple times per day.

## Question 3:
   I never have to check the application for anything, I usually know what 
I’m getting and where I’m going.
   
I use the app to check what kinds of soup there are.

   I use the website to check different menu’s throughout the day.

## Question 4:
   I haven’t ever eaten at Grins, mostly because I don’t know what they serve.
  
 I don’t eat at EBI. I don’t like Asian food much.

   EBI. It is so far and the food wasn’t the best the one time I went. 

## Question 5:
   I eat where I do on campus mostly for convenience and not having to worry
about what to eat elsewhere on campus.
   
Like I said, I just follow the places with the best soups on campus.

   I usually eat at the same locations because I know what they have
and my friends eat there.

## Question 6:  
 We should have a pizza place on campus, like Pi used to be.
   
Just ice cream.  

   I’d really appreciate if Vanderbilt offered a barbecue option somewhere on campus.

## Question 7:
   The best way for me to receive menu information would be somehow through my phone.
   
If I could get text updates for different soups on campus. 

   I use my computer because I check while I’m in class. My phone would be better but
the app is clunky to use while walking.

## Question 8:
   I prefer menus that are both static and dynamic, like the pub used to be when
they had the weekly specials.
   
I like menus that change so I can have different options.

   I like menus that change. I get more options that way.

## Question 9:
   I will often not eat on campus because I do not feel like eating at any of the
places I normally do. 
  
 Yes. All the time. 

   Yeah, usually on the weekends.

## Question 10:
   I walk to commons a few times a week to get food. They have more options that 
will change on occasion.
   
I eat at commons almost every day. Its a mixture of walking there for food and
having class there near meal times. 
   
I eat at commons a couple times a week, but not for any reason in particular.
They’ve added a bunch of new options though.

# Requirements

As a Vanderbilt Student, I want to interface with the application through a 
text messaging interface such that I text a phone number and receive a text
message response. 

As a Vanderbilt Student, I want the application’s response to send me the 
Vanderbilt menu for corresponding to the date, time, and location inputs.

As a Vanderbilt Student, I want to be able to inquire about all Vanderbilt
dining locations listed on the website through the application. 

As a Vanderbilt Student, I want to know if the dining hall I inquire about
will close within an hour of the message.

As a Vanderbilt Student, I want to know if the a dining hall is closed at the
time of inquiry.

As a Vanderbilt student, I want to use the application as quickly and with as
few clicks as absolutely possible.

# Development Approach

The development of this project began with contextual inquiries of Vanderbilt students
living on Highland and Blakemore quads. These students have the longest walks to main
campus, where most of our dining is located, and therefore their decisions as to where
to go for dinner are much more significant than someone who lives close to various dining
halls. These students were interviewed to see gauge how much a menu text messaging 
application would be useful to them, and to empathize with them about their Vanderbilt
campus dining experience. These questions aimed to get at how knowledgeable they were
about dining options and how they make their dining decisions in order to figure out
how to implement the vision of the application to best meet user needs. 

From the interview data acquired from the contextual inquiries, requirements were created
for the application. Again, empathizing with the users was crucial in developing the
requirements, such that the end product will be as usable and impactful as possible. These
requirements were extremely important to the application development process, as they define
an end point for the application, and help point the app features in the right direction.
These requirements were written in a User Story fashion, where the story represents a user
and a certain functionality that the application should have. The requirements were written
in this way in order to maintain focus on the user centered development, and continue 
empathizing with the user even through the development process. By having the requirements so
closely linked to the user, it hopefully makes it harder for the end product to deviate from
the user requests. 

From the requirements, the application design and architecture began to take place. This involves mocking
up potential solutions to meet requirement requests, for example, an application that has a text
messaging interface and the code behind it, and verifying that other requirements will work with
these different designs. Although this involved thinking about the code needed for the application, 
it does not require that actual code be written, just planned out for later implementation. This was
done in order to have a development plan going forward, again to help insure that the application is
built cohesively and preventing problems in development wherever they may occur. This also helps to
insure that the requirements of the application are attainable and realistic for later development.
Additionally in this architecture phase, rough time estimates and priority rankings are attributed
to each requirement for the application. This is so that in development, the different tasks can be
performed in the order that makes the most sense to the application, and will yield  the most viable
product in case of significant problem or failure. Additionally, this involves creating components
of the application that are not highly dependent on each other such that testing is easier than 
harder. 

After the architecture of the application is completed, the development and testing phases of the
project can begin. This is where the actual code is written and tested for functionality. This step
is obviously crucial to the development process, as it is the development itself. Development involved
taking a requirement starting with the most important and completing it within a period of time based
on its difficulty. The requirement was broken into smaller pieces in Architecture, and now those 
pieces are created in code. After the small components are created, they are tested individually to 
insure correctness. Additionally, they are tested to make sure that they are not riddled with side 
effects or are heavily dependent on side effects in order to work. This will significantly help later
developers if the project is ever to be expanded upon. After the components are fully tested, they are
combined to create a product or add the functionality that the requirement called for, and the development
moves to the next most important requirement. This development cycle insures that at the end of the project,
there will at least be a functioning base level prototype because development started with the most fundamental,
base level components of the application and built into it from there. Additionally, this makes sure
that requirements are completed in full and not left half complete and implemented into the application.
Furthermore, this development means that the application requirements can be changed somewhat without
necessarily changing large portions of the application. 