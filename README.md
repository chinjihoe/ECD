# ECD

### Server

Requirements:
* Node JS V4 or higher!
* MYSQL Server


**API Documentation** of the server has to be generated.

Step 1) Install apiDoc on your computer (-g = global)
```bash
npm install -g apiDoc
```
  
Step 2) In your terminal cd to the root of the project.
```bash
# example
cd /home/name/ECD
```
Step 3) Run apiDoc to get the documentation.
```bash
apiDoc -i server/ -o apiDoc/
```

Step 4) You can now open the index.html found in apiDoc to read the documentation.
