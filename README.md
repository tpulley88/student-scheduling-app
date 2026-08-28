# Student Scheduling App

A native Android application for organizing academic terms, courses, instructors, and assessments. It demonstrates a multi-screen Java application backed by Room persistence and Android architecture components.

> Portfolio note: this is a sanitized snapshot of a 2021 university project, preserved to demonstrate Android and local-persistence fundamentals.

## Highlights

- Term, course, instructor, and assessment management
- Room entities, DAOs, and local persistence
- Course status and date validation
- Start and end notifications
- Portrait and landscape layouts
- Reports that group courses and assessments

## Technology

- Java 8
- Android SDK 32
- AndroidX lifecycle components
- Room persistence library
- Material Components and ConstraintLayout

## Running locally

Requirements:

- JDK 11
- Android Studio with Android SDK 32 available

Open the repository in Android Studio and allow Gradle to synchronize. Create a standard `local.properties` through Android Studio; machine-specific SDK paths are intentionally excluded.

This is a historical Android project and uses its original 2021-era toolchain. A future modernization pass should update the Android Gradle plugin, target SDK, and architecture dependencies.

## Project context

Originally created by Terin Pulley as a university mobile-development project. It is published as a portfolio example with the assessment prompt, grading material, release binaries, and generated build files excluded.

## Privacy

The app stores data locally. Use fictional course and instructor information only; no student records are included in this repository.

The public repository starts with this sanitized portfolio edition; the original classroom history was intentionally not imported.
