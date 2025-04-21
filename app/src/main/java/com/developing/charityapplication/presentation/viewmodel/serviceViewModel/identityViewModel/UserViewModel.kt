package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.identityModel.UserM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IUserRepo
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.loading.LoadingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
   private val repo: IUserRepo
): ViewModel(){

    // region --- Methods ---

    fun createAccountUser(request: RequestCreateUser){
        viewModelScope.launch{
            LoadingViewModel.enableLoading(true)
            try {
                val result = repo.createAccount(request)
                _userInfo.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    // endregion

    // region --- Properties ---

    val userInfo: StateFlow<ResponseM<UserM>?>
        get() = _userInfo

    // endregion

    // region --- Fields ---

    private val _userInfo = MutableStateFlow<ResponseM<UserM>?>(null)

    // endregion

}