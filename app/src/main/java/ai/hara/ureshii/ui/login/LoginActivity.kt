package ai.hara.ureshii.ui.login

import ai.hara.ureshii.ui.main.MainActivity
import ai.hara.ureshii.ui.theme.UreshiiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            UreshiiTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    ConstraintLayoutContent()
                }
            }
            if(viewModel.isLoggedIn){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @Composable
    fun ConstraintLayoutContent() {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            // Create references for the composables to constrain
            val (username, password, register, login) = createRefs()
            val registerTextState = remember { mutableStateOf(TextFieldValue("mohammadasheri")) }
            TextField(
                label = { Text("نام کاربری") },
                value = registerTextState.value,
                onValueChange = { registerTextState.value = it },
                modifier = Modifier
                    .constrainAs(username) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            val passwordTextState = remember { mutableStateOf(TextFieldValue("12345")) }
            TextField(
                label = { Text("گذرواژه") },
                value = passwordTextState.value,
                onValueChange = { passwordTextState.value = it },
                modifier = Modifier.constrainAs(password) {
                    top.linkTo(username.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Button(
                modifier = Modifier.constrainAs(register) {
                    top.linkTo(password.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(login.start)
                },
                onClick = {
                    viewModel.register(registerTextState.value.text, passwordTextState.value.text)
                }) {
                Text(text = "ثبت نام", color = Color.White)
            }
            Button(modifier = Modifier.constrainAs(login) {
                top.linkTo(password.bottom, margin = 16.dp)
                start.linkTo(register.end)
                end.linkTo(parent.end)
            }, onClick = {
                viewModel.login(
                    registerTextState.value.text,
                    passwordTextState.value.text
                )
            }) {
                Text(text = "ورود", color = Color.White)
            }
        }
    }
}