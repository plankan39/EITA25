/*
 * Shows user info from local pwfile.
 *  
 * Usage: userinfo username
 */

#define _XOPEN_SOURCE
#include <crypt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pwdblib.h"   /* include header declarations for pwdblib.c */

/* Define some constants. */
#define USERNAME_SIZE (32)
#define NOUSER (-1)
#define SALT_SIZE (2)
#define MAX_TRIES (5)


int print_info(const char *username)
{
  struct pwdb_passwd *p = pwdb_getpwnam(username);
  if (p != NULL) {
    printf("Name: %s\n", p->pw_name);
    printf("Passwd: %s\n", p->pw_passwd);
    printf("Uid: %u\n", p->pw_uid);
    printf("Gid: %u\n", p->pw_gid);
    printf("Real name: %s\n", p->pw_gecos);
    printf("Home dir: %s\n",p->pw_dir);
    printf("Shell: %s\n", p->pw_shell);
	return 0;
  } else {
    return NOUSER;
  }
}


void read_username(char *username)
{
  printf("login: ");
  fgets(username, USERNAME_SIZE, stdin);

  /* remove the newline included by getline() */
  username[strlen(username) - 1] = '\0';
}


int checkPassword(char username[], char password[])
{
  char salt[SALT_SIZE];
  struct pwdb_passwd *pwd = pwdb_getpwnam(username);

  // checks if the user excist
  if (pwd == NULL) {
    return 0;
  }

  // copies the salt from pwd struct
  strncpy(salt, pwd->pw_passwd, SALT_SIZE);

  /* compares hashed password with the encrypted
     salt and password. Returns 1 if same else 0 */
  return strcmp(pwd->pw_passwd, crypt(password, salt)) == 0 ? 1 : 0;
}


int main(int argc, char **argv)
{
  char username[USERNAME_SIZE];
  char *password;

  int tries = 0;

  while (tries < MAX_TRIES) {
    read_username(username);
    password = getpass("Password: ");

    // if password match Authentication is successfull
    if (checkPassword(username, password)) {
      printf("User authenticated successfully");
      return 0;
    }

    printf("Unknown user or incorrect password.\n");

    /* uncomment to implement max number of tries  */
    // tries++; 
  }

  printf("Maximum number of tries exceeded (%d)\n", MAX_TRIES);
  return 1;
}
