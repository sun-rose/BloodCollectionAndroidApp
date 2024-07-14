package com.example.bloodcollectionandroidapp.ProfileFragment;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bloodcollectionandroidapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile extends Fragment {

    private ImageView profileImage;
    private ImageButton editData;
    private EditText profileName, profileMobile, profileLocation, profileBloodGroup;
    Button update_info;
    private SwitchCompat profileDonorSwitch;
    DatabaseReference reference_image;
    FirebaseUser fUser;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
   //private StorageTask uploadTask;
    String mode_switch_text="Taker";
    Boolean enable=false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        profileName = view.findViewById(R.id.profile_name_et);
        profileName.setEnabled(enable);
        profileMobile = view.findViewById(R.id.profile_mobile);
        profileMobile.setEnabled(enable);
        profileLocation = view.findViewById(R.id.profile_location);
        profileLocation.setEnabled(enable);
        profileBloodGroup = view.findViewById(R.id.profile_blood_group);
        profileBloodGroup.setEnabled(enable);
        profileDonorSwitch = view.findViewById(R.id.mode_switch);
        profileDonorSwitch.setEnabled(enable);


        //
        storageReference = FirebaseStorage.getInstance().getReference();
        update_info = view.findViewById(R.id.submit_btn);
        editData = view.findViewById(R.id.edit_button);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fUser != null) {
            reference_image = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());
            reference_image.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);

                    if (imageUrl != null&& !imageUrl.equalsIgnoreCase("Default")) {
                        Glide.with(requireContext()).load(imageUrl).into(profileImage);
                    }
                    else{
                        profileImage.setImageResource(R.drawable.ic_launcher_background);
                    }
                    String pName = snapshot.child("username").getValue(String.class);
                    if (imageUrl != null) {
                        profileName.setText(pName);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




        profileImage.setOnClickListener(v -> openImage());

        editData.setOnClickListener(v -> {
            if(!enable){
                enable=true;
                enableProfileEditing(enable);
                update_info.setVisibility(View.VISIBLE);

            }
            else{
                enable=false;
                enableProfileEditing(enable);
                update_info.setVisibility(View.GONE);
            }


        });

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fUser != null) {
                    Profile.this.updateUserProfile();
                }
                else {
                    Profile.this.addUserProfile();

                }
                refreshFragment();
            }

        });


        profileDonorSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileDonorSwitch.isChecked()) {
                    mode_switch_text = profileDonorSwitch.getTextOn().toString();

                   // submit_essentials.setVisibility(View.VISIBLE);
                   // essential_approved = false;
                }
                else {
                    mode_switch_text = profileDonorSwitch.getTextOff().toString();
                  //  submit_essentials.setVisibility(View.GONE);
                   // essential_approved = true;
                }
                Toast.makeText(requireContext(), "Switch mode changed", Toast.LENGTH_LONG).show(); // display the current state for switch's
            }
        });

        loadprofile();

        return view;
    }



    private void loadprofile(){

        if (fUser != null) {
            DatabaseReference user_info_ref = FirebaseDatabase.getInstance().getReference("UsersDetails").child(fUser.getUid());
            user_info_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserDetails uDetails = snapshot.getValue(UserDetails.class);
                    // UserDetails uDetails = new UserDetails();

                    if (uDetails != null) {
                        uDetails.setUsername(snapshot.child("username").getValue().toString());
                        uDetails.setMobile(snapshot.child("mobile").getValue().toString());
                        uDetails.setLocation(snapshot.child("location").getValue().toString());
                        uDetails.setBloodGroup(snapshot.child("blood_group").getValue().toString());
                        uDetails.setUserMode(snapshot.child("user_mode").getValue().toString());


                        profileName.setText(uDetails.getUsername());
                        profileMobile.setText(uDetails.getMobile());
                        profileLocation.setText(uDetails.getLocation());
                        profileBloodGroup.setText(uDetails.getBloodGroup());
                        profileDonorSwitch.setText(uDetails.getUserMode());
                        profileDonorSwitch.setChecked(uDetails.getUserMode().equalsIgnoreCase("Donor"));

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        } else {
            Log.e("DataSnapshot", "User object is null");
            Profile.this.addUserProfile();
        }

    }



    private void enableProfileEditing(boolean enable) {
        profileName.setEnabled(enable);
        profileMobile.setEnabled(enable);
        profileLocation.setEnabled(enable);
        profileBloodGroup.setEnabled(enable);
        profileDonorSwitch.setEnabled(enable);

        if (enable) {
            profileName.requestFocus();
            profileMobile.requestFocus();
            profileLocation.requestFocus();
            profileBloodGroup.requestFocus();
        }

    }

    private void refreshFragment() {
getActivity().getSupportFragmentManager().beginTransaction().commit();

    }




    private void updateUserProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersDetails").child(fUser.getUid());

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", profileName.getText().toString());
        userDetails.put("mobile", profileMobile.getText().toString());
        userDetails.put("location", profileLocation.getText().toString());
        userDetails.put("blood_group", profileBloodGroup.getText().toString());
        userDetails.put("user_mode", mode_switch_text);


        reference.updateChildren(userDetails)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Your details updated successfully.");
                    Toast.makeText(requireContext(), "User details updated.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: " + e);
                    Toast.makeText(requireContext(), "Failed to update user details.", Toast.LENGTH_SHORT).show();
                });

    }
    private void addUserProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersDetails").child(fUser.getUid());

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", profileName.getText().toString());
        userDetails.put("mobile", profileMobile.getText().toString());
        userDetails.put("location", profileLocation.getText().toString());
        userDetails.put("blood_group", profileBloodGroup.getText().toString());
        userDetails.put("user_mode", mode_switch_text);

        reference.setValue(userDetails)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Your details updated successfully.");
                    Toast.makeText(requireContext(), "User details added.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: " + e);
                    Toast.makeText(requireContext(), "Failed to update user details.", Toast.LENGTH_SHORT).show();
                });

    }

    private void openImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //  intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        // MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();


        return MimeTypeMap.getFileExtensionFromUrl(contentResolver.getType(uri));
    }

    public final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK || result.getResultCode() == IMAGE_REQUEST) {
                if (result.getData() != null) {
                    //uploadImage.setEnabled(true);
                    imageUri = result.getData().getData();
                    assert imageUri != null;
                    Glide.with(requireContext()).load(imageUri).into(profileImage);
                    uploadImage(imageUri);


                }
            } else {
                Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });


    private void uploadImage(Uri uri) {
        if (uri != null) {
            StorageReference ref = storageReference.child("uploaded_images/" + profileName.getText() + "/" + UUID.randomUUID().toString());
//uploadTask=ref.putFile(uri);
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String sUri = uri.toString();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("imageUrl", sUri);
                            reference_image.updateChildren(map);
                            //User user= new User();
                           // user.setImage_url(sUri);
                        }
                    });
                }
            });


        }
    }
}