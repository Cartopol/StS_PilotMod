# Contributing

We welcome contributions, including from newcomers with no prior modding experience.

## Table of contents

* [Resources and links](#resources-and-links)
* [How to build from source](#how-to-build-from-source)
* [How to contribute art](#how-to-contribute-art)
* [How to contribute sound effects](#how-to-contribute-sound-effects)
* [How to contribute translations to other languages](#how-to-contribute-translations-to-other-languages)
* [How to contribute changes](#how-to-contribute-changes)

## Resources and links


## How to build from source

1. Create an environment variable named `STEAMAPPS_PATH`
    * On Windows, open a Command Prompt and run `setx STEAMAPPS_PATH "C:\Program Files (x86)\Steam\steamapps"`
    * On Mac, open `~/.bash_profile` and add a line like `export STEAMAPPS_PATH="~/Library/Application Support/Steam/steamapps"`
    * Adjust the paths accordingly if you have installed Steam to a different location 
1. Install the [Oracle Java 8 JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) (*not* Java 9+)
1. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/) (the free Community edition is fine)
1. Through Steam, install Slay the Spire (stable branch)
1. From the Steam Workshop, install "Mod the Spire", "BaseMod", and "StSLib"
1. Clone the repository
1. Open the project in IntelliJ IDEA:
    1. Choose "Import Project"
    1. Choose the repository folder
    1. Select "Maven"
    1. Press next a few times, all other settings can be left at defaults 
1. Follow the [these instructions from the StS-DefaultModBase wiki](https://github.com/Gremious/StS-DefaultModBase/wiki/Step-3:-Packaging-and-Playing-the-Default;-Writing-Your-First-Mod!) to build the mod package and debug it

## How to contribute changes

This project uses GitHub Pull Requests to handle merging contributed changes. If you're new to using GitHub or Pull Requests, here's the TL;DR for the workflow I like to use:

### First-time setup

1. Create your own fork of this repository by clicking the "Fork" button at the top right of this page (you'll need a GitHub account)
1. [Install Git](https://git-scm.com/downloads)
1. From a command prompt, run the following (replace `your_username` with your GitHub username):
    ```bash
    # This is where the folder for the mod code will be placed inside of.
    # This can be wherever you like; I like something short like "C:\code" or "C:\repos".
    # Don't use a path with spaces in it!
    cd C:/code
    
    # This will clone the repository into a new folder named "pilot" inside the directory you picked above
    git clone --origin upstream [[git origin repo url]]
    cd pilot
   
    # Replace "your_username" with your GitHub username!
    git remote add my_fork https://github.com/your_username/pilot.git

    # These are "aliases", helper commands that will make it easier to use GitHub Pull Requests
    git config --global alias.newfeature "!git checkout master && git pull && git checkout -b"
    git config --global alias.pushtofork "!git push --set-upstream my_fork HEAD"
    ```

### Each time you want to start a new feature/contribution...

1. (recommended) Discuss your idea on the Discord first, to make sure noone else is already working on the same thing
1. Run `git newfeature my-cool-feature-name`
1. Make your code changes, build and test locally
1. Use the usual `git add *` and `git commit -m 'description of changes'` commands to make local commits
    * If you're brand new to git, https://try.github.io has some good learning resources

### When you're ready to submit your changes to be reviewed and merged...

1. Run `git pushtofork` from your feature branch
1. Create a Pull Request on GitHub